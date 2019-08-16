package com.spaceboost.challenge.service;

import com.spaceboost.challenge.exception.CampaignNotFoundException;
import com.spaceboost.challenge.exception.IdExistsException;
import com.spaceboost.challenge.model.Campaign;
import com.spaceboost.challenge.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampaignService {

    private CampaignRepository campaignRepository;

    @Autowired
    public CampaignService(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    public Campaign getCampaign(int campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId);
        if (campaign != null) {
            return campaign;
        } else {
            throw new CampaignNotFoundException(campaignId);
        }
    }

    public List<Campaign> getAll() {
        return campaignRepository.getAll();
    }

    public Campaign create(Campaign campaign) {
        if (campaignRepository.findById(campaign.getId()) == null) {
            return campaignRepository.add(campaign);
        } else {
            throw new IdExistsException("CampaignId: " + campaign.getId());
        }
    }

}
