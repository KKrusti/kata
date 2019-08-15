package com.spaceboost.challenge;

import com.spaceboost.challenge.controller.CampaignController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private CampaignController campaignController;

    @Test
    public void contextLoads() {
        assertThat(campaignController).isNotNull();
    }

}
