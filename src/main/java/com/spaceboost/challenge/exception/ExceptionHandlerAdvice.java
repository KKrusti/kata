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

    public static final String COMBINATION_ID_ERROR_MESSAGE = "The combination of identifiers provided do not exist -";
    public static final String ID_EXISTS_MESSAGE = "The selected id already exists -";

    @ExceptionHandler({WrongIdentifiersException.class})
    public ResponseEntity handleException(WrongIdentifiersException e) {
        ApiError apiError = new ApiError(COMBINATION_ID_ERROR_MESSAGE + e.getMessage());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IdExistsException.class})
    public ResponseEntity handleException(IdExistsException e) {
        ApiError apiError = new ApiError(ID_EXISTS_MESSAGE + e.getMessage());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }
}
