package com.spaceboost.challenge.service;

import com.spaceboost.challenge.model.campaign.Campaign;
import com.spaceboost.challenge.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CampaignService {

    @Autowired
    CampaignRepository campaignRepository;
//
//    public CampaignService(CampaignRepository campaignRepository){
//        this.campaignRepository = campaignRepository;
//    }

    public Campaign getCampaign(int id){
        return new Campaign(id);
    }


}
