package com.spaceboost.challenge;

import com.spaceboost.challenge.controller.AdGroupController;
import com.spaceboost.challenge.controller.CampaignController;
import com.spaceboost.challenge.controller.KeywordController;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
class ApplicationTests {

    @Autowired
    private CampaignController campaignController;

    @Autowired
    private AdGroupController adGroupController;

    @Autowired
    private KeywordController keywordController;

    @Test
    void contextLoads() {
        assertThat(campaignController).isNotNull();
        assertThat(adGroupController).isNotNull();
        assertThat(keywordController).isNotNull();

    }

}
