package com.spaceboost.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AdGroupNotFoundException extends RuntimeException {

    private final Integer adGroupId;

    public AdGroupNotFoundException(int adGroupId) {
        super("adGroup-not-found-" + adGroupId);
        this.adGroupId = adGroupId;
    }

}