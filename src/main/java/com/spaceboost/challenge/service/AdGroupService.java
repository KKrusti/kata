package com.spaceboost.challenge.service;

import com.spaceboost.challenge.exception.AdGroupNotFoundException;
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

    public List<AdGroup> getAdGroupsForCampaign(long campaignId) {
        List<AdGroup> adGroups = adGroupRepository.getAll().stream().filter(x -> x.getCampaignId() == campaignId).collect(Collectors.toList());
        return adGroups;
    }


}
