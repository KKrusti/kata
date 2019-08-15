package com.spaceboost.challenge.service;

import com.spaceboost.challenge.Application;
import com.spaceboost.challenge.exception.CampaignNotFoundException;
import com.spaceboost.challenge.model.Campaign;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = {CampaignService.class, Application.class})
public class CampaignServiceTest {

    private static final int EXISTING_CAMPAIGN_ID = 1;
    private static final int NON_EXISTING_CAMPAIGN_ID = 99999;
    private static final int INITIAL_CAMPAIGN_NUMBER = 5;

    @Autowired
    private CampaignService campaignService;

    @Test
    public void withExistingCampaign_findById_campaignFound() {
        Campaign retrievedCampaign = campaignService.getCampaign(EXISTING_CAMPAIGN_ID);
        Campaign expectedCampaign = new Campaign(EXISTING_CAMPAIGN_ID);
        Assertions.assertEquals(expectedCampaign, retrievedCampaign);
    }

    @Test
    public void withNonExistingCampaign_findById_exceptionThrown() {
        Assertions.assertThrows(CampaignNotFoundException.class, () -> {
            campaignService.getCampaign(NON_EXISTING_CAMPAIGN_ID);
        });
    }

    @Test
    public void getAll() {
        List<Campaign> campaigns = campaignService.getAll();

        Assertions.assertEquals(INITIAL_CAMPAIGN_NUMBER, campaigns.size());
    }

}
