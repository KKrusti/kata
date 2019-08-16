package com.spaceboost.challenge.service;

import com.spaceboost.challenge.exception.AdGroupNotFoundException;
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
    private AdGroupService adGroupService;

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

    public Keyword create(Keyword keyword) throws IdExistsException, AdGroupNotFoundException, WrongIdentifiersException {
        keywordIsNew(keyword.getId());
        adGroupAndCampaignExist(keyword.getCampaignId(), keyword.getAdGroupId());
        return keywordRepository.add(keyword);
    }

    private void keywordIsNew(int keywordId) throws IdExistsException {
        if (keywordRepository.findById(keywordId) != null) {
            throw new IdExistsException("Keyword with id " + keywordId + " already exists");
        }
    }

    /**
     * This method checks two statements:
     * 1) AdGroup exists
     * 2) The campaignId of the keyword is the same as the campaignId of the AdGroup since both should be the same.
     *
     * @param campaignId
     * @param adGroupId
     * @throws AdGroupNotFoundException  if the AdGroup doesn't exist
     * @throws WrongIdentifiersException if there's a mismatch between campaignId of the keyword and the campaignId of the AdGroup
     */
    private void adGroupAndCampaignExist(int campaignId, int adGroupId) throws AdGroupNotFoundException, WrongIdentifiersException {
        if (adGroupService.getAdGroupWithCampaign(campaignId, adGroupId) == null) {
            throw new AdGroupNotFoundException(adGroupId);
        }
    }

}
