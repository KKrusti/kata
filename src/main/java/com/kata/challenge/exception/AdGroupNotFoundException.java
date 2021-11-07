package com.kata.challenge.exception;

public class AdGroupNotFoundException extends RuntimeException {

    public AdGroupNotFoundException(int adGroupId) {
        super(String.valueOf(adGroupId));
    }

}
