package com.spaceboost.challenge.controller;

import com.spaceboost.challenge.model.AdGroup;
import com.spaceboost.challenge.service.AdGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
//@RequestMapping(value = "/adGroups")
public class AdGroupController {

    @Autowired
    private AdGroupService adGroupService;

    @Autowired
    public AdGroupController(AdGroupService adGroupService) {
        this.adGroupService = adGroupService;
    }

    @GetMapping("campaigns/{campaignId}/adGroups/{adGroupId}")
    public ResponseEntity<AdGroup> getAdGroup(@PathVariable("campaignId") int campaignId, @PathVariable("adGroupId") int adGroupId) {
        AdGroup adGroup = adGroupService.getAdGroupWithCampaign(campaignId, adGroupId);
        return ResponseEntity.ok(adGroup);
    }


//     @Autowired
//    private CampaignService campaignService;
//
//    @Autowired
//    public CampaignController(CampaignService campaignService) {
//        this.campaignService = campaignService;
//    }
//
//    @GetMapping("campaigns/{id}")
//    public ResponseEntity<Campaign> getCampaign(@PathVariable("id") int id) {
//        return ResponseEntity.ok(campaignService.getCampaign(id));
//    }
}
