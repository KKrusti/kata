package com.kata.challenge.exception;

import com.kata.challenge.model.ApiError;
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
    public static final String CAMPAIGN_NOT_FOUND = "campaign-not-found-";
    public static final String AD_GROUP_NOT_FOUND = "adGroup-not-found-";

    @ExceptionHandler({WrongIdentifiersException.class})
    public ResponseEntity handleException(WrongIdentifiersException e) {
        ApiError apiError = new ApiError(COMBINATION_ID_ERROR_MESSAGE + e.getMessage());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({IdExistsException.class})
    public ResponseEntity handleException(IdExistsException e) {
        ApiError apiError = new ApiError(ID_EXISTS_MESSAGE + e.getMessage());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({CampaignNotFoundException.class})
    public ResponseEntity handleException(CampaignNotFoundException e) {
        ApiError apiError = new ApiError(CAMPAIGN_NOT_FOUND + e.getMessage());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({AdGroupNotFoundException.class})
    public ResponseEntity handleException(AdGroupNotFoundException e) {
        ApiError apiError = new ApiError(AD_GROUP_NOT_FOUND + e.getMessage());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

}
