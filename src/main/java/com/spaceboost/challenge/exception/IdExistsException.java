package com.spaceboost.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class IdExistsException extends RuntimeException {

    public IdExistsException(String errorMessage) {
        super(errorMessage);
    }

}
