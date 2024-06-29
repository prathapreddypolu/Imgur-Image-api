package com.ecom.imgur.exception;

import com.ecom.imgur.common.Constant;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@AllArgsConstructor
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiError> userException(UserException ex) {
        ApiError apiError = new ApiError(Constant.USER_ERROR_CODE,
                Constant.USER_ERROR_DESC);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAuthenticationException.class)
    public ResponseEntity<ApiError> userAuthenticationException(UserAuthenticationException ex) {
        ApiError apiError = new ApiError(Constant.USER_ERROR_CODE,
                Constant.USER_ERROR_DESC);
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }
}
