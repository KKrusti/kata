package com.spaceboost.challenge.service;

import com.spaceboost.challenge.Application;
import com.spaceboost.challenge.exception.AdGroupNotFoundException;
import com.spaceboost.challenge.model.AdGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

}
