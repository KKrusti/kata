package com.spaceboost.challenge.controller;

import com.spaceboost.challenge.model.campaign.Campaign;
import com.spaceboost.challenge.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class CampaignController {

    private final CampaignService campaignService;

    @Autowired
    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping("campaigns/{id}")
    public ResponseEntity<Campaign> getCampaign(@PathVariable("id") int id) {
        return ResponseEntity.ok(this.campaignService.getCampaign(id));
    }

}
