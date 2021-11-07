package com.kata.challenge.controller;

import com.kata.challenge.model.AdGroup;
import com.kata.challenge.service.AdGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import java.net.URI;

@RestController
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

    @PostMapping("adGroups")
    public ResponseEntity<AdGroup> createAdGroup(@RequestBody AdGroup adGroup) {
        AdGroup createdAdGroup = adGroupService.create(adGroup);
        URI selfLink = MvcUriComponentsBuilder.fromController(getClass()).path("campaigns/{campaignId}/adGroups/{id}").buildAndExpand(createdAdGroup.getCampaignId(),
                createdAdGroup.getId()).toUri();
        return ResponseEntity.created(selfLink).body(createdAdGroup);
    }

}
