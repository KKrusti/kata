package com.spaceboost.challenge.controller;

import com.spaceboost.challenge.model.Keyword;
import com.spaceboost.challenge.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class KeywordController {

    @Autowired
    private KeywordService keywordService;

    @Autowired
    public KeywordController(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    @GetMapping("campaigns/{campaignId}/adGroups/{adGroupId}/keywords/{keywordId}")
    public ResponseEntity<Keyword> getAdGroup(@PathVariable("campaignId") int campaignId, @PathVariable("adGroupId") int adGroupId, @PathVariable("keywordId") int keywordId) {
        Keyword keyword = keywordService.getKeywordWithCampaignAndAdGroupId(campaignId, adGroupId, keywordId);
        return ResponseEntity.ok(keyword);
    }

}
