package com.kata.challenge.exception;

public class IdExistsException extends RuntimeException {

    public IdExistsException(String errorMessage) {
        super(errorMessage);
    }

}
