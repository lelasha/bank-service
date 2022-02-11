package com.emulator.bankservice.service;

import com.emulator.bankservice.controller.response.UserTransactionDto;
import com.emulator.bankservice.entity.OperationType;
import com.emulator.bankservice.entity.UserEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public interface TransactionService {

    List<UserTransactionDto> getTransactionDtoByAccountId(String accountNumber,
                                                          Instant startDate,
                                                          Instant endDate);

    void buildAndSaveTransaction(UserEntity userEntity,
                                 OperationType operationType,
                                 BigDecimal amount);
}
