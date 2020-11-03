package com.spaceboost.challenge.service;

import com.spaceboost.challenge.Application;
import com.spaceboost.challenge.exception.AdGroupNotFoundException;
import com.spaceboost.challenge.exception.CampaignNotFoundException;
import com.spaceboost.challenge.exception.IdExistsException;
import com.spaceboost.challenge.exception.WrongIdentifiersException;
import com.spaceboost.challenge.model.AdGroup;
import com.spaceboost.challenge.model.CostPerCampaign;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {AdGroupService.class, Application.class})
class AdGroupServiceTest {

    private static final int EXISTING_ADGROUP_ID = 5;
    private static final int NON_EXISTING_ADGROUP_ID = 99999;
    private static final int INITIAL_ADGROUP_NUMBER = 15;


    @Autowired
    private AdGroupService adGroupService;

    @Test
    void withExistingAdGroup_findById_adGroupFound() {
        AdGroup retrievedAdGroup = adGroupService.getAdgroup(EXISTING_ADGROUP_ID);
        AdGroup expectedAdGroup = new AdGroup(EXISTING_ADGROUP_ID, 0, 54, 23, 28.17);
        Assertions.assertEquals(expectedAdGroup, retrievedAdGroup);
    }

    @Test
    void withNonExistingAdGroup_findById_exceptionThrown() {
        Assertions.assertThrows(AdGroupNotFoundException.class, () -> {
            adGroupService.getAdgroup(NON_EXISTING_ADGROUP_ID);
        });
    }

    @Test
    void withExistingCampaign_getAdGroupsForCampaign_getList() {
        List<AdGroup> adGroups = adGroupService.getAdGroupsForCampaign(1);

        List<AdGroup> expectedAdGroups = new ArrayList<>();
        expectedAdGroups.add(new AdGroup(8, 1, 5, 0, 3.32d));
        expectedAdGroups.add(new AdGroup(12, 1, 87, 13, 101.73d));
        expectedAdGroups.add(new AdGroup(13, 1, 55, 18, 99.47d));

        Assertions.assertEquals(expectedAdGroups, adGroups);
    }

    @Test
    void withWrongIdentifierCombination_getAdGroupWithCampaign_throwException() {
        Assertions.assertThrows(WrongIdentifiersException.class, () ->
                adGroupService.getAdGroupWithCampaign(1, 1));
    }

    @Test
    void getAll() {
        List<AdGroup> adGroups = adGroupService.getAll();

        Assertions.assertEquals(INITIAL_ADGROUP_NUMBER, adGroups.size());
    }

    @Test
    void withNewAdGroup_create_adGroupCreated() {
        AdGroup adGroup = new AdGroup(50, 2, 1, 0, 0d);
        int initialSize = adGroupService.getAll().size();
        AdGroup createdAdgroup = adGroupService.create(adGroup);
        int finalSize = adGroupService.getAll().size();

        Assertions.assertEquals(adGroup, createdAdgroup);
        Assertions.assertTrue(initialSize < finalSize);
    }

    @Test
    void withRepeatedId_create_errorThrown() {
        AdGroup existingAdGroup = new AdGroup(1, 1, 1, 1, 1d);
        Assertions.assertThrows(IdExistsException.class, () -> adGroupService.create(existingAdGroup));
    }

    @Test
    void withNonExistingCampaign_create_errorThrown() {
        AdGroup adGroup = new AdGroup(512, 112, 1, 1, 1d);
        Assertions.assertThrows(CampaignNotFoundException.class, () -> adGroupService.create(adGroup));
    }

    @Test
    void withDefaultValues_getCostPerCampaign_correctValues() {
        Map<Integer, CostPerCampaign> costPerCampaignMap = adGroupService.getCostPerCampaign();

        CostPerCampaign expectedCampaign0 = new CostPerCampaign(441.97, 113.0);
        CostPerCampaign expectedCampaign1 = new CostPerCampaign(204.52, 31.0);
        CostPerCampaign expectedCampaign2 = new CostPerCampaign(84.47, 44.0);
        CostPerCampaign expectedCampaign3 = new CostPerCampaign(463.42, 59.0);
        CostPerCampaign expectedCampaign4 = new CostPerCampaign(70.89, 28.0);

        Assertions.assertEquals(expectedCampaign0, costPerCampaignMap.get(0));
        Assertions.assertEquals(expectedCampaign1, costPerCampaignMap.get(1));
        Assertions.assertEquals(expectedCampaign2, costPerCampaignMap.get(2));
        Assertions.assertEquals(expectedCampaign3, costPerCampaignMap.get(3));
        Assertions.assertEquals(expectedCampaign4, costPerCampaignMap.get(4));
    }

}
