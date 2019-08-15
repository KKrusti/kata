package com.spaceboost.challenge.service;

import com.spaceboost.challenge.exception.AdGroupNotFoundException;
import com.spaceboost.challenge.exception.WrongIdentifiersException;
import com.spaceboost.challenge.model.AdGroup;
import com.spaceboost.challenge.repository.AdGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdGroupService {

    private AdGroupRepository adGroupRepository;

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

    public List<AdGroup> getAdGroupsForCampaign(int campaignId) {
        return adGroupRepository.getAll().stream().filter(x -> x.getCampaignId() == campaignId).collect(Collectors.toList());
    }

    public AdGroup getAdGroupWithCampaign(int campaignId, int adGroupId) throws WrongIdentifiersException {
        AdGroup adGroup = getAdgroup(adGroupId);
        if (adGroup.getCampaignId() == campaignId) {
            return adGroup;
        } else {
            throw new WrongIdentifiersException("CampaignId = " + campaignId + " , adGroupId = " + adGroupId);
        }
    }

}
