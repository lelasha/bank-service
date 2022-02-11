package com.emulator.bankservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //used in atm-service as well
    UNEXPECTED_ERROR(1000),
    USER_NOT_FOUND(1001),
    CARD_NOT_VALID(1002),
    INVALID_ARGUMENT(1005),

    //specific for this service
    INVALID_AUTH_METHOD(2000),
    NOT_ENOUGH_BALANCE(2001);

    private final int code;
}
