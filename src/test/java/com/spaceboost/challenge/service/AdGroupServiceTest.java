package com.spaceboost.challenge.service;

import com.spaceboost.challenge.Application;
import com.spaceboost.challenge.exception.AdGroupNotFoundException;
import com.spaceboost.challenge.exception.WrongIdentifiersException;
import com.spaceboost.challenge.model.AdGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {AdGroupService.class, Application.class})
public class AdGroupServiceTest {

    private static final int EXISTING_ADGROUP_ID = 5;
    private static final int NON_EXISTING_ADGROUP_ID = 99999;


    @Autowired
    private AdGroupService adGroupService;

    @Test
    public void withExistingAdGroup_findById_adGroupFound() {
        AdGroup retrievedAdGroup = adGroupService.getAdgroup(EXISTING_ADGROUP_ID);
        AdGroup expectedAdGroup = new AdGroup(EXISTING_ADGROUP_ID, 0, 54, 23, 28.17f);
        Assertions.assertEquals(expectedAdGroup, retrievedAdGroup);
    }

    @Test
    public void withNonExistingAdGroup_findById_exceptionThrown() {
        Assertions.assertThrows(AdGroupNotFoundException.class, () -> {
            adGroupService.getAdgroup(NON_EXISTING_ADGROUP_ID);
        });
    }

    @Test
    public void withExistingCampaign_getAdGroupsForCampaign_getList() {
        List<AdGroup> adGroups = adGroupService.getAdGroupsForCampaign(1);

        List<AdGroup> expectedAdGroups = new ArrayList<>();
        expectedAdGroups.add(new AdGroup(8, 1, 5, 0, 3.32f));
        expectedAdGroups.add(new AdGroup(12, 1, 87, 13, 101.73f));
        expectedAdGroups.add(new AdGroup(13, 1, 55, 18, 99.47f));

        Assertions.assertEquals(expectedAdGroups, adGroups);
    }

    @Test
    public void withWrongIdentifierCombination_getAdGroupWithCampaign_throwException() {
        Assertions.assertThrows(WrongIdentifiersException.class, () ->
                adGroupService.getAdGroupWithCampaign(1, 1));
    }

    @Test
    public void withNewAdGroup_create_adGroupCreated() {
        AdGroup adGroup = new AdGroup(50, 50, 1, 1, 1f);
        AdGroup createdAdgroup = adGroupService.create(adGroup);

        Assertions.assertEquals(adGroup, createdAdgroup);
    }

}
