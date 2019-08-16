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

    public Campaign create(Campaign campaign) throws IdExistsException {
        campaignIsNew(campaign.getId());
        return campaignRepository.add(campaign);
    }

    private void campaignIsNew(int campaignId) throws IdExistsException {
        if (campaignRepository.findById(campaignId) != null) {
            throw new IdExistsException("Campaign with id " + campaignId + " already exists");
        }
    }

}
