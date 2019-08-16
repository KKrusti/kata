package com.spaceboost.challenge.service;

import com.spaceboost.challenge.exception.IdExistsException;
import com.spaceboost.challenge.exception.KeywordNotFoundException;
import com.spaceboost.challenge.exception.WrongIdentifiersException;
import com.spaceboost.challenge.model.Keyword;
import com.spaceboost.challenge.repository.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeywordService {

    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    public KeywordService(KeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }

    public Keyword getKeyword(int keywordId) {
        Keyword keyword = keywordRepository.findById(keywordId);
        if (keyword != null) {
            return keyword;
        } else {
            throw new KeywordNotFoundException(keywordId);
        }
    }

    public List<Keyword> getAll() {
        return keywordRepository.getAll();
    }

    public Keyword getMostClicked() {
        return keywordRepository.getAll().stream().max(Comparator.comparing(Keyword::getClicks)).orElseThrow(() -> new IllegalArgumentException());
    }

    public Keyword getMostConversions() {
        return keywordRepository.getAll().stream().max(Comparator.comparing(Keyword::getConversions)).orElseThrow(() -> new IllegalArgumentException());
    }

    public List<Keyword> getKeywordsForCampaignId(int campaignId) {
        return keywordRepository.getAll().stream().filter(x -> x.getCampaignId() == campaignId).collect(Collectors.toList());
    }

    public Keyword getKeywordWithCampaignAndAdGroupId(int campaignId, int adGroupId, int keywordId) {
        Keyword keyword = getKeyword(keywordId);
        if (keyword.getCampaignId() == campaignId && keyword.getAdGroupId() == adGroupId) {
            return keyword;
        } else {
            throw new WrongIdentifiersException("CampaignId = " + campaignId + " , adGroupId = " + adGroupId + " , KeywordId = " + keywordId);
        }
    }

    public Keyword create(Keyword keyword) throws IdExistsException {
        if (keywordRepository.findById(keyword.getId()) == null) {
            return keywordRepository.add(keyword);
        } else {
            throw new IdExistsException("Keyword with id " + keyword.getId() + " already exists");
        }
    }

}
