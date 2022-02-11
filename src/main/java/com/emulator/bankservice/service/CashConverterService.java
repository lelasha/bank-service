package com.emulator.bankservice.service;

import java.math.BigDecimal;

public interface CashConverterService {
    BigDecimal convertToLowestDenomination(BigDecimal amount);

    BigDecimal convertToHighestDenomination(BigDecimal amount);
}
