package com.spaceboost.challenge.repository;

import com.spaceboost.challenge.exception.IdExistsException;
import com.spaceboost.challenge.model.Campaign;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CampaignRepository.class)
public class CampaignRepositoryTest {

    @Autowired
    private CampaignRepository campaignRepository;

    private static final int EXISTING_CAMPAIGN_ID = 1;
    private static final int NON_EXISTING_CAMPAIGN_ID = 99999;

    @Test
    public void withExistingCampaign_findById_campaignFound() {
        Campaign retrievedCampaign = campaignRepository.findById(EXISTING_CAMPAIGN_ID);
        Campaign expectedCampaign = new Campaign(EXISTING_CAMPAIGN_ID);
        Assertions.assertEquals(expectedCampaign, retrievedCampaign);
    }

    @Test
    public void withNonExistingCampaign_findById_campaignNotFound() {
        Assertions.assertNull(campaignRepository.findById(NON_EXISTING_CAMPAIGN_ID));
    }

    @Test
    public void withExistingDataInJson_getAll_campaignsFound() {
        List<Campaign> campaigns = campaignRepository.getAll();

        Assertions.assertTrue(campaigns.size() > 0);
    }

    @Test
    public void withNewCampaign_add_campaignCreated() {
        int previousSize = campaignRepository.getAll().size();
        Campaign campaign = new Campaign(99);
        campaignRepository.add(campaign);
        int postSize = campaignRepository.getAll().size();
        Assertions.assertTrue(previousSize < postSize);
    }

    @Test
    public void withExistingCampaignId_add_exceptionThrown() {
        Campaign campaign = new Campaign(0);
        Assertions.assertThrows(IdExistsException.class, () -> campaignRepository.add(campaign));
    }
}
