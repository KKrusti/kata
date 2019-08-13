package com.spaceboost.challenge.controller;

import com.spaceboost.challenge.service.AdGroupService;
import com.spaceboost.challenge.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/adGroups")
public class AdGroupController {

    @Autowired
    private AdGroupService adGroupService;

//    @Autowired
//    public CampaignController(CampaignService campaignService) {
//        this.campaignService = campaignService;
//    }

//    @GetMapping("campaigns/{id}")
//    public ResponseEntity<Campaign> getCampaign(@PathVariable("id") int id) {
//        return ResponseEntity.ok(this.campaignService.getCampaign(id));
//    }

}
