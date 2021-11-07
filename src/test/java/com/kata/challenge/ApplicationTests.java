package com.kata.challenge;

import com.kata.challenge.controller.AdGroupController;
import com.kata.challenge.controller.CampaignController;
import com.kata.challenge.controller.KeywordController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private CampaignController campaignController;

    @Autowired
    private AdGroupController adGroupController;

    @Autowired
    private KeywordController keywordController;

    @Test
    public void contextLoads() {
        assertThat(campaignController).isNotNull();
        assertThat(adGroupController).isNotNull();
        assertThat(keywordController).isNotNull();

    }

}
