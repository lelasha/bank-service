package com.emulator.bankservice.service.impl;

import com.emulator.bankservice.controller.response.UserTransactionDto;
import com.emulator.bankservice.entity.OperationType;
import com.emulator.bankservice.entity.UserEntity;
import com.emulator.bankservice.entity.UserTransactionEntity;
import com.emulator.bankservice.repo.UserTransactionEntityRepo;
import com.emulator.bankservice.service.CashConverterService;
import com.emulator.bankservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final UserTransactionEntityRepo userTransactionEntityRepo;
    private final CashConverterService cashConverterService;


    @Override
    public List<UserTransactionDto> getTransactionDtoByAccountId(String accountNumber,
                                                                 Instant startDate,
                                                                 Instant endDate) {
        log.info("Getting transaction list by account id {}", accountNumber);
        List<UserTransactionDto> dtos = new ArrayList<>();
        userTransactionEntityRepo.findAllByUserEntityAccountNumberAndTransactionDateBetween(accountNumber,startDate,endDate)
                .forEach(userTransactionEntity ->
                        buildUserTransactionDtos(accountNumber, dtos, userTransactionEntity));
        return dtos;
    }

    @Override
    public void buildAndSaveTransaction(UserEntity userEntity,
                                        OperationType operationType,
                                        BigDecimal amount) {
        log.info("saving {} transaction with amount {} for user {}",
                operationType.name(), amount, userEntity.getAccountNumber());

        UserTransactionEntity transaction = UserTransactionEntity.builder()
                .userEntity(userEntity)
                .amount(amount)
                .operationType(operationType)
                .build();
        userTransactionEntityRepo.save(transaction);
    }

    private void buildUserTransactionDtos(String accountNumber,
                                          List<UserTransactionDto> dtos,
                                          UserTransactionEntity userTransactionEntity) {
        BigDecimal finalAmount = cashConverterService.convertToHighestDenomination(userTransactionEntity.getAmount());
        dtos.add(UserTransactionDto.builder()
                .accountNumber(accountNumber)
                .amount(finalAmount)
                .operationType(userTransactionEntity.getOperationType())
                .transactionDate(userTransactionEntity.getTransactionDate())
                .build());
    }
}
