package com.emulator.bankservice.controller.response;

import com.emulator.bankservice.entity.OperationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public class UserTransactionDto {
    private String accountNumber;
    private Instant transactionDate;
    private OperationType operationType;
    private BigDecimal amount;
}
