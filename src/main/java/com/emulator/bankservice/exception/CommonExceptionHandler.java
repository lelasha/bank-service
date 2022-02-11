package com.emulator.bankservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CommonExceptionHandler {


    @ExceptionHandler(value = NoSuchAuthMethodException.class)
    public ResponseEntity<ErrorResponse> NoSuchAuthMethodExceptionHandle(NoSuchAuthMethodException exception) {
        log.error("Such auth method doesn't exist {}", exception.getMessage(), exception);
        return buildErrorResponse(
                ErrorCode.INVALID_AUTH_METHOD,
                exception.getMessage(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> UserNotFoundExceptionHandle(UserNotFoundException exception) {
        log.error("User not found {}", exception.getMessage(), exception);
        return buildErrorResponse(
                ErrorCode.USER_NOT_FOUND,
                exception.getMessage(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = OutOfBalanceException.class)
    public ResponseEntity<ErrorResponse> outOfBalanceExceptionHandle(OutOfBalanceException exception) {
        log.error("Requested amount isn't possible to withdraw {}", exception.getMessage(), exception);
        return buildErrorResponse(
                ErrorCode.NOT_ENOUGH_BALANCE,
                exception.getMessage(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error("Invalid argument for the method: {}",exception.getMessage(),exception);
        return buildErrorResponse(
                ErrorCode.INVALID_ARGUMENT,
                exception.getMessage(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorResponse> runTimeExceptionHandle(RuntimeException exception) {
        log.error("Runtime exception {}", exception.getMessage(), exception);

        return buildErrorResponse(
                ErrorCode.UNEXPECTED_ERROR,
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private ResponseEntity<ErrorResponse> buildErrorResponse(ErrorCode errorCode,
                                                             String message,
                                                             HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus)
                .body(ErrorResponse.builder()
                        .errorCode(errorCode)
                        .message(message)
                        .build());
    }
}
