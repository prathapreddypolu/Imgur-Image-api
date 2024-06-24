package com.ecom.imgur.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{

    @Getter
    private final String errorCode;
    private final String message;

    /**
     * Constructs a new instance of the ApiException with the specified error code and message.
     *
     * @param errorCode The error code associated with the exception.
     * @param message   The human-readable message associated with the exception.
     * @param cause     The underlying cause of the exception.
     */
    public ApiException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public  String getMessage() {
        return message;
    }
}
