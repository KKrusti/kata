package com.spaceboost.challenge.service;

import com.spaceboost.challenge.model.Campaign;
import com.spaceboost.challenge.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    public List<Campaign> getAllCampaigns(){
        return campaignRepository.getAll();
    }
//
//    public CampaignService(CampaignRepository campaignRepository){
//        this.campaignRepository = campaignRepository;
//    }

//    public Campaign getCampaign(int id){
//        return new Campaign(id);
//    }


}
