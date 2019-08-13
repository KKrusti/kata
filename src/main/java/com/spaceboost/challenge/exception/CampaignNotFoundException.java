package com.spaceboost.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CampaignNotFoundException extends RuntimeException{

    private final Integer campaignId;

    public CampaignNotFoundException(int campaignId){
        super("campaign-not-found-" +campaignId);
        this.campaignId = campaignId;
    }

}
