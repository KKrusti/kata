package com.spaceboost.challenge.repository;

import com.spaceboost.challenge.model.AdGroup;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AdGroupRepository.class)
class AdGroupRepositoryTest {

    @Autowired
    private AdGroupRepository adGroupRepository;

    private static final int EXISTING_ADGROUP_ID = 5;
    private static final int NON_EXISTING_ADGROUP_ID = 99999;

    @Test
    void withExistingAdGroup_findById_adGroupFound() {
        AdGroup retrievedAdGroup = adGroupRepository.findById(EXISTING_ADGROUP_ID).get();
        AdGroup expectedAdGroup = new AdGroup(EXISTING_ADGROUP_ID, 0, 54, 23, 28.17);
        Assertions.assertEquals(expectedAdGroup, retrievedAdGroup);
    }

    @Test
    void withNonExistingAdGroup_findByNonExistingId_adGroupNotFound() {
        assertThat(adGroupRepository.findById(NON_EXISTING_ADGROUP_ID)).isEmpty();
    }

    @Test
    void withExistingDataInJson_getAll_adGroupsFound() {
        List<AdGroup> adGroups = adGroupRepository.getAll();

        Assertions.assertTrue(adGroups.size() > 0);
    }

}
