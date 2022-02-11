package com.emulator.bankservice.service.impl;

import com.emulator.bankservice.authorization.AuthorizationFactory;
import com.emulator.bankservice.controller.request.AuthMethod;
import com.emulator.bankservice.controller.response.UserTransactionDto;
import com.emulator.bankservice.entity.OperationType;
import com.emulator.bankservice.entity.UserEntity;
import com.emulator.bankservice.exception.OutOfBalanceException;
import com.emulator.bankservice.exception.UserNotFoundException;
import com.emulator.bankservice.repo.UserEntityRepo;
import com.emulator.bankservice.service.BankService;
import com.emulator.bankservice.service.CashConverterService;
import com.emulator.bankservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.emulator.bankservice.entity.OperationType.DEPOSIT;
import static com.emulator.bankservice.entity.OperationType.WITHDRAW;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {
    private final UserEntityRepo userEntityRepo;
    private final CashConverterService cashConverterService;
    private final TransactionService transactionService;
    private final AuthorizationFactory authorizationFactory;


    @Override
    @Transactional(readOnly = true)
    public List<UserTransactionDto> getTransactionsByAccountNumberAndDateBetween(String accountNumber,
                                                                                 Instant startDate,
                                                                                 Instant endDate) {
        return transactionService.getTransactionDtoByAccountId(accountNumber,startDate,endDate);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Object deposit(BigDecimal amount, String accountNumber) {
        BigDecimal amountLowest = cashConverterService.convertToLowestDenomination(amount);

        return cashConverterService.convertToHighestDenomination(
                userEntityRepo.findById(accountNumber)
                        .map(userEntity -> depositUserBalance(amountLowest, userEntity))
                        .orElseThrow(UserNotFoundException::new));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Object withdraw(BigDecimal amount, String accountNumber) {
        BigDecimal amountLowest = cashConverterService.convertToLowestDenomination(amount);

        return cashConverterService.convertToHighestDenomination(userEntityRepo.findById(accountNumber)
                .map(userEntity -> withdrawUserBalance(amountLowest, userEntity))
                .orElseThrow(UserNotFoundException::new));
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getBalance(String accountNumber) {
        BigDecimal balance = userEntityRepo.findById(accountNumber)
                .map(UserEntity::getBalance)
                .orElseThrow(UserNotFoundException::new);
        return cashConverterService.convertToHighestDenomination(balance);
    }

    @Override
    @Transactional(readOnly = true)
    public String checkIfUserExists(String cardNumber) {
        log.info("Checking if user exists with card number: {}",cardNumber);

        return userEntityRepo.findByCardNumber(cardNumber)
                .map(UserEntity::getAccountNumber)
                .orElseThrow(UserNotFoundException::new);
    }

    // for simplicity hashing mechanism is removed
    @Override
    @Transactional(readOnly = true)
    public boolean auth(String credential, AuthMethod authMethod, String accountNumber) {
        UserEntity user = findUserEntityById(accountNumber);

        return authorizationFactory.getAuthorization(authMethod).authenticate(credential,user);
    }

    private BigDecimal withdrawUserBalance(BigDecimal amountLowest, UserEntity userEntity) {
        BigDecimal finalValue = userEntity.getBalance().subtract(amountLowest);
        if (finalValue.compareTo(BigDecimal.ZERO) < 0) {
            throw new OutOfBalanceException("Not enough balance to withdraw");
        }
        return setUserBalanceAndPersist(userEntity, finalValue, WITHDRAW);
    }

    private BigDecimal depositUserBalance(BigDecimal amountLowest, UserEntity userEntity) {
        BigDecimal finalValue = userEntity.getBalance().add(amountLowest);
        return setUserBalanceAndPersist(userEntity, finalValue, DEPOSIT);
    }

    private BigDecimal setUserBalanceAndPersist(
            UserEntity userEntity,
            BigDecimal finalValue,
            OperationType operationType) {
        log.info("Doing {} operation for user {} with final balance of {}",
                operationType.name(),userEntity.getAccountNumber(),finalValue);

        userEntity.setBalance(finalValue);
        userEntityRepo.save(userEntity);
        transactionService.buildAndSaveTransaction(
                userEntity,
                operationType,
                finalValue);

        return finalValue;
    }

    private UserEntity findUserEntityById(String accountNumber) {
        Optional<UserEntity> optionalUserEntity = userEntityRepo.findById(accountNumber);
        if (optionalUserEntity.isEmpty()) {
            throw new UserNotFoundException("User with accountNumber " + accountNumber + " doesn't exist");
        }
        return optionalUserEntity.get();
    }

}
