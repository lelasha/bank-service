package com.emulator.bankservice.Exception;

public class NoSuchAuthMethodException extends RuntimeException {
    public NoSuchAuthMethodException(String message) {
        super(message);
    }
}
