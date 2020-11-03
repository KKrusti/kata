package com.spaceboost.challenge.controller;

import com.spaceboost.challenge.model.Campaign;
import com.spaceboost.challenge.model.CostConversionRateResult;
import com.spaceboost.challenge.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import java.net.URI;

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

    @PostMapping("campaigns")
    public ResponseEntity<Campaign> createCampaign(@RequestBody Campaign campaign) {
        Campaign createdCampaign = campaignService.create(campaign);
        URI selfLink = MvcUriComponentsBuilder.fromController(getClass()).path("campaigns/{id}").buildAndExpand(createdCampaign.getId()).toUri();
        return ResponseEntity.created(selfLink).body(createdCampaign);
    }

    @GetMapping("worstCostConversionRate")
    public ResponseEntity<CostConversionRateResult> getWorstCostConversionRate() {
        CostConversionRateResult result = campaignService.getWorstCostPerConversionRate();
        return ResponseEntity.ok(result);
    }
}
