package com.spaceboost.challenge.service;

import com.spaceboost.challenge.exception.AdGroupNotFoundException;
import com.spaceboost.challenge.exception.IdExistsException;
import com.spaceboost.challenge.exception.WrongIdentifiersException;
import com.spaceboost.challenge.model.AdGroup;
import com.spaceboost.challenge.model.CostPerCampaign;
import com.spaceboost.challenge.repository.AdGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdGroupService {

    private AdGroupRepository adGroupRepository;

    @Autowired
    private CampaignService campaignService;

    @Autowired
    public AdGroupService(AdGroupRepository adGroupRepository) {
        this.adGroupRepository = adGroupRepository;
    }

    public AdGroup getAdgroup(int adGroupId) {
        AdGroup adGroup = adGroupRepository.findById(adGroupId);
        if (adGroup != null) {
            return adGroup;
        } else {
            throw new AdGroupNotFoundException(adGroupId);
        }
    }

    public List<AdGroup> getAll() {
        return adGroupRepository.getAll();
    }

    public List<AdGroup> getAdGroupsForCampaign(int campaignId) {
        return adGroupRepository.getAll().stream().filter(x -> x.getCampaignId() == campaignId).collect(Collectors.toList());
    }

    public AdGroup getAdGroupWithCampaign(int campaignId, int adGroupId) {
        AdGroup adGroup = getAdgroup(adGroupId);
        if (adGroup.getCampaignId() == campaignId) {
            return adGroup;
        } else {
            throw new WrongIdentifiersException("CampaignId = " + campaignId + " , adGroupId = " + adGroupId);
        }
    }

    public AdGroup create(AdGroup adGroup) {
        adGroupIsNew(adGroup.getId());
        campaignExists(adGroup.getCampaignId());
        return adGroupRepository.add(adGroup);
    }

    public Map<Integer, CostPerCampaign> getCostPerCampaign() {
        Map<Integer, CostPerCampaign> costAndConversionMap = new HashMap<>();

        adGroupRepository.getAll().stream().collect(Collectors.groupingBy(AdGroup::getCampaignId)).forEach((campaignId, keyWords) ->
                costAndConversionMap.put(campaignId, new CostPerCampaign(
                        BigDecimal.valueOf(keyWords.stream().mapToDouble(AdGroup::getCost).sum()).setScale(2, RoundingMode.CEILING).doubleValue(),
                        keyWords.stream().mapToDouble(AdGroup::getConversions).sum())));

        return costAndConversionMap;
    }

    private void campaignExists(int campaignId) {
        campaignService.getCampaign(campaignId);
    }

    private void adGroupIsNew(int adGroupId) {
        if (adGroupRepository.findById(adGroupId) != null) {
            throw new IdExistsException("AdGroup with id " + adGroupId + " already exists");
        }
    }

}
