package com.emulator.bankservice.service.impl;

import com.emulator.bankservice.service.CashConverterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class CashConverterServiceImpl implements CashConverterService {


    @Override
    public BigDecimal convertToLowestDenomination(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(100));
    }

    @Override
    public BigDecimal convertToHighestDenomination(BigDecimal amount) {
        return amount.divide(BigDecimal.valueOf(100));
    }
}
