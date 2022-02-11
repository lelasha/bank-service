package com.emulator.bankservice.service;

import com.emulator.bankservice.controller.request.AuthMethod;
import com.emulator.bankservice.controller.response.UserTransactionDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public interface BankService {
    BigDecimal getBalance(String accountNumber);

    Object deposit(BigDecimal amount, String accountNumber);

    Object withdraw(BigDecimal amount, String accountNumber);

    boolean auth(String credential, AuthMethod authMethod, String accountNumber);

    String checkIfUserExists(String cardNumber);

    List<UserTransactionDto> getTransactionsByAccountNumberAndDateBetween(String accountNumber,
                                                                          Instant startDate,
                                                                          Instant endDate);
}
