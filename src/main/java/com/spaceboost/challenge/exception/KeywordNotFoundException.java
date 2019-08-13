package com.spaceboost.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class KeywordNotFoundException extends RuntimeException {

    private final Integer keywordId;

    public KeywordNotFoundException(int keywordId) {
        super("keyword-not-found-" + keywordId);
        this.keywordId = keywordId;
    }

}
