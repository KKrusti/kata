package com.spaceboost.challenge.controller;

import com.spaceboost.challenge.model.Keyword;
import com.spaceboost.challenge.service.KeywordService;
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

    @GetMapping("keywords/mostClicked")
    public ResponseEntity<Keyword> getMostClicked() {
        Keyword keyword = keywordService.getMostClicked();
        return ResponseEntity.ok(keyword);
    }

    @GetMapping("keywords/mostConversions")
    public ResponseEntity<Keyword> getMostConversions() {
        Keyword keyword = keywordService.getMostConversions();
        return ResponseEntity.ok(keyword);
    }

    @PostMapping("keywords")
    public ResponseEntity<Keyword> createKeyword(@RequestBody Keyword keyword) {
        Keyword createdKeyword = keywordService.create(keyword);
        URI selfLink =
                MvcUriComponentsBuilder.fromController(getClass()).path("campaigns/{campaignId}/adGroups/{adGroupId}/keywords/{id}").buildAndExpand(createdKeyword.getCampaignId(), createdKeyword.getAdGroupId(), createdKeyword.getId()).toUri();
        return ResponseEntity.created(selfLink).body(createdKeyword);
    }

}
