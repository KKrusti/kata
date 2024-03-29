package com.kata.challenge.service;

import com.kata.challenge.exception.AdGroupNotFoundException;
import com.kata.challenge.exception.IdExistsException;
import com.kata.challenge.exception.KeywordNotFoundException;
import com.kata.challenge.exception.WrongIdentifiersException;
import com.kata.challenge.model.CostPerCampaign;
import com.kata.challenge.model.Keyword;
import com.kata.challenge.repository.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class KeywordService {

    private KeywordRepository keywordRepository;

    @Autowired
    private AdGroupService adGroupService;

    @Autowired
    public KeywordService(KeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }

    public Keyword getKeyword(int keywordId) {
        return keywordRepository.findById(keywordId).orElseThrow(() -> new KeywordNotFoundException(keywordId));
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

    public Map<Integer, CostPerCampaign> getCostPerCampaign() {
        Map<Integer, CostPerCampaign> costAndConversionMap = new HashMap<>();

        keywordRepository.getAll().stream().collect(Collectors.groupingBy(Keyword::getCampaignId)).forEach((campaignId, keyWords) ->
                costAndConversionMap.put(campaignId, new CostPerCampaign(
                        keyWords.stream().mapToDouble(Keyword::getCost).sum(),
                        keyWords.stream().mapToDouble(Keyword::getConversions).sum())));

        return costAndConversionMap;
    }

    public Keyword create(Keyword keyword) throws AdGroupNotFoundException {
        keywordIsNew(keyword.getId());
        adGroupAndCampaignExist(keyword.getCampaignId(), keyword.getAdGroupId());
        return keywordRepository.add(keyword);
    }

    private void keywordIsNew(int keywordId) {
        if (keywordRepository.findById(keywordId).isPresent()) {
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
    private void adGroupAndCampaignExist(int campaignId, int adGroupId) throws AdGroupNotFoundException{
        adGroupService.getAdGroupWithCampaign(campaignId, adGroupId);
    }

}
