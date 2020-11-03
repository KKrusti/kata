package com.spaceboost.challenge.repository;

import com.spaceboost.challenge.model.Campaign;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CampaignRepository.class)
class CampaignRepositoryTest {

    @Autowired
    private CampaignRepository campaignRepository;

    private static final int EXISTING_CAMPAIGN_ID = 1;
    private static final int NON_EXISTING_CAMPAIGN_ID = 99999;

    @Test
    void withExistingCampaign_findById_campaignFound() {
        Campaign retrievedCampaign = campaignRepository.findById(EXISTING_CAMPAIGN_ID).get();
        Campaign expectedCampaign = new Campaign(EXISTING_CAMPAIGN_ID);
        Assertions.assertEquals(expectedCampaign, retrievedCampaign);
    }

    @Test
    void withNonExistingCampaign_findById_campaignNotFound() {
        assertThat(campaignRepository.findById(NON_EXISTING_CAMPAIGN_ID)).isEmpty();
    }

    @Test
    void withExistingDataInJson_getAll_campaignsFound() {
        List<Campaign> campaigns = campaignRepository.getAll();

        Assertions.assertTrue(campaigns.size() > 0);
    }

}
