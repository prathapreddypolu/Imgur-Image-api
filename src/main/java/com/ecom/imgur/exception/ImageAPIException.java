package com.ecom.imgur.exception;

public
class ImageAPIException extends ApiException{

    /**
     * Constructs a new instance of the InternalServerErrorException with the specified error code, message, and cause.
     *
     * @param errorCode The error code associated with the exception.
     * @param message   The human-readable message associated with the exception.
     * @param cause     The underlying cause of the exception.
     */
    /*public
    ImageAPIException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }*/

    /**
     * Constructs a new instance of the InternalServerErrorException with the specified error code and message.
     *
     * @param errorCode The error code associated with the exception.
     * @param message   The human-readable message associated with the exception.
     */
    public
    ImageAPIException(String errorCode, String message) {
        super(errorCode, message, null);
    }
}
