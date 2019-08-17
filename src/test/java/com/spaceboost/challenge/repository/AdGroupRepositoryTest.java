package com.spaceboost.challenge.repository;

import com.spaceboost.challenge.model.AdGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AdGroupRepository.class)
public class AdGroupRepositoryTest {

    @Autowired
    private AdGroupRepository adGroupRepository;

    private static final int EXISTING_ADGROUP_ID = 5;
    private static final int NON_EXISTING_ADGROUP_ID = 99999;

    @Test
    public void withExistingAdGroup_findById_adGroupFound() {
        AdGroup retrievedAdGroup = adGroupRepository.findById(EXISTING_ADGROUP_ID);
        AdGroup expectedAdGroup = new AdGroup(EXISTING_ADGROUP_ID, 0, 54, 23, 28.17);
        Assertions.assertEquals(expectedAdGroup, retrievedAdGroup);
    }

    @Test
    public void withNonExistingAdGroup_findById_adGroupNotFound() {
        Assertions.assertNull(adGroupRepository.findById(NON_EXISTING_ADGROUP_ID));
    }

    @Test
    public void withExistingDataInJson_getAll_adGroupsFound() {
        List<AdGroup> adGroups = adGroupRepository.getAll();

        Assertions.assertTrue(adGroups.size() > 0);
    }

}
