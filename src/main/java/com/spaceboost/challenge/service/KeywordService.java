package com.spaceboost.challenge.service;

import com.spaceboost.challenge.model.Campaign;
import com.spaceboost.challenge.model.Keyword;
import com.spaceboost.challenge.repository.CampaignRepository;
import com.spaceboost.challenge.repository.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeywordService {

    @Autowired
    private KeywordRepository keywordRepository;

    public List<Keyword> getAllKeywords(){
        return keywordRepository.getAll();
    }
//
//    public CampaignService(CampaignRepository campaignRepository){
//        this.campaignRepository = campaignRepository;
//    }

//    public Campaign getCampaign(int id){
//        return new Campaign(id);
//    }


}