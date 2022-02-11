package com.emulator.bankservice.exception;

public class NoSuchAuthMethodException extends RuntimeException {

    public NoSuchAuthMethodException(String message) {
        super(message);
    }
}
