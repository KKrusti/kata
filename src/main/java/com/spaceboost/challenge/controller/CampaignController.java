package com.spaceboost.challenge.controller;

import com.spaceboost.challenge.model.Campaign;
import com.spaceboost.challenge.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @Autowired
    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping("campaigns/{id}")
    public ResponseEntity<Campaign> getCampaign(@PathVariable("id") int id) {
        return ResponseEntity.ok(campaignService.getCampaign(id));
    }

}
