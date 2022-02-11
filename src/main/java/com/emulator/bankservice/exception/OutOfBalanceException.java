package com.emulator.bankservice.exception;

public class OutOfBalanceException extends RuntimeException {
    public OutOfBalanceException(String message) {
        super(message);
    }
}
