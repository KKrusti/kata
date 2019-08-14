package com.spaceboost.challenge.service;

import com.spaceboost.challenge.exception.AdGroupNotFoundException;
import com.spaceboost.challenge.model.AdGroup;
import com.spaceboost.challenge.repository.AdGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
