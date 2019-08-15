package com.spaceboost.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongIdentifiersException extends RuntimeException {

    public WrongIdentifiersException(String errorMessage) {
        super(errorMessage);
    }

}
