package com.spaceboost.challenge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spaceboost.challenge.model.Campaign;
import com.spaceboost.challenge.service.CampaignService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CampaignController.class)
public class CampaignControllerTest {

    private static final int CAMPAIGN_ID = 1;

    @MockBean
    private CampaignService mockCampaignService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void withWrightId_shouldReturnCampaign() throws Exception {
        Campaign campaign = new Campaign(CAMPAIGN_ID);
        when(mockCampaignService.getCampaign(CAMPAIGN_ID)).thenReturn(campaign);

        mvc.perform(get("/campaigns/" + CAMPAIGN_ID))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(CAMPAIGN_ID)));
    }

    @Test
    public void withCorrectId_createCampaign_created() throws Exception {
        Campaign campaign = new Campaign(50);
        when(mockCampaignService.create(campaign)).thenReturn(campaign);

        mvc.perform(post("/campaigns/campaign")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createdCampaignInJson(campaign)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    private static String createdCampaignInJson(Campaign campaign) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(campaign);
    }

}
