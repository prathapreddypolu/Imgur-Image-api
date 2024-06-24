package com.ecom.imgur.exception;

public
class ImageAPIException extends ApiException{

   public
    ImageAPIException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }


    public ImageAPIException(String errorCode, String message) {

       super(errorCode, message, null);
    }
}
