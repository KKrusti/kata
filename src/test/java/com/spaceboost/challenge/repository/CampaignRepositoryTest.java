package com.spaceboost.challenge.repository;

import com.spaceboost.challenge.exception.CampaignNotFoundException;
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
        Campaign campaign = campaignRepository.findById(EXISTING_CAMPAIGN_ID);

        Assertions.assertNotNull(campaign);
    }

    @Test
    public void withNonExistingCampaign_findById_exceptionThrown() {
        Assertions.assertThrows(CampaignNotFoundException.class, () -> {
            campaignRepository.findById(NON_EXISTING_CAMPAIGN_ID);
        });
    }

    @Test
    public void withExistingDataInJson_getAll_campaignsFound() {
        List<Campaign> campaigns = campaignRepository.getAll();

        Assertions.assertTrue(!campaigns.isEmpty());
    }
}
