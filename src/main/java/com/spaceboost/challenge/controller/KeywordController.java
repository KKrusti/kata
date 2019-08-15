package com.spaceboost.challenge.controller;

import com.spaceboost.challenge.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class KeywordController {

    @Autowired
    private KeywordService keywordService;

    @Autowired
    public KeywordController(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

//    @GetMapping("campaigns/{campaignId}/adGroups/{adGroupId}")
//    public ResponseEntity<Keyword> getAdGroup(@PathVariable("campaignId") int campaignId, @PathVariable("adGroupId") int adGroupId, @PathVariable("keywordId") int keywordId) {
//        Keyword keyword = keywordService.getKeywordWithCampaignAndAdGroupId(campaignId, adGroupId, keywordId);
//        return ResponseEntity.ok(keyword);
//    }

}
