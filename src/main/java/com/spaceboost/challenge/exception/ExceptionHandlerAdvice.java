package com.spaceboost.challenge.exception;

import com.spaceboost.challenge.model.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    public static final String ERROR_MESSAGE = "The combination of identifiers provided do not exist -";

    @ExceptionHandler({WrongIdentifiersException.class})
    public ResponseEntity handleException(WrongIdentifiersException e) {
        ApiError apiError = new ApiError(ERROR_MESSAGE + e.getMessage());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
