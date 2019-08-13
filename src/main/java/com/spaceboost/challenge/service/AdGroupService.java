package com.spaceboost.challenge.service;

import com.spaceboost.challenge.model.AdGroup;
import com.spaceboost.challenge.model.Campaign;
import com.spaceboost.challenge.repository.AdGroupRepository;
import com.spaceboost.challenge.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdGroupService {

    @Autowired
    private AdGroupRepository adGroupRepository;

    public List<AdGroup> getAllAdGroups(){
        return adGroupRepository.getAll();
    }
//
//    public CampaignService(CampaignRepository campaignRepository){
//        this.campaignRepository = campaignRepository;
//    }

//    public Campaign getCampaign(int id){
//        return new Campaign(id);
//    }


}
