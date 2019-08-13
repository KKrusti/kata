package com.spaceboost.challenge.controller;

import com.spaceboost.challenge.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/keywords")
public class KeywordController {

    @Autowired
    private KeywordService keywordService;

//    @Autowired
//    public CampaignController(CampaignService campaignService) {
//        this.campaignService = campaignService;
//    }

//    @GetMapping("campaigns/{id}")
//    public ResponseEntity<Campaign> getCampaign(@PathVariable("id") int id) {
//        return ResponseEntity.ok(this.campaignService.getCampaign(id));
//    }

}
