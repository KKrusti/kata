package com.kata.challenge.service;

import com.kata.challenge.Application;
import com.kata.challenge.exception.CampaignNotFoundException;
import com.kata.challenge.exception.IdExistsException;
import com.kata.challenge.model.Campaign;
import com.kata.challenge.model.CostConversionRateResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
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

    @Test
    public void createCampaign() {
        Campaign campaign = new Campaign(999);
        int initialSize = campaignService.getAll().size();
        Campaign createdCampaign = campaignService.create(campaign);
        int finalSize = campaignService.getAll().size();

        Assertions.assertEquals(campaign, createdCampaign);
        Assertions.assertTrue(initialSize < finalSize);
    }

    @Test
    public void withExistingCampaignId_add_exceptionThrown() {
        Campaign campaign = new Campaign(0);
        Assertions.assertThrows(IdExistsException.class, () -> campaignService.create(campaign));
    }

    @Test
    public void withDefaultValues_getCostPerConversionRate_correctValues() {
        CostConversionRateResult costConversionRateResult = campaignService.getWorstCostPerConversionRate();

        CostConversionRateResult expected = new CostConversionRateResult(3, new BigDecimal("7.92"));

        Assertions.assertEquals(expected, costConversionRateResult);
    }

}
