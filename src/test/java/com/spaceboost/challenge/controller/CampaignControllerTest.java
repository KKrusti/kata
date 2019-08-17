package com.spaceboost.challenge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spaceboost.challenge.exception.ExceptionHandlerAdvice;
import com.spaceboost.challenge.exception.IdExistsException;
import com.spaceboost.challenge.model.ApiError;
import com.spaceboost.challenge.model.Campaign;
import com.spaceboost.challenge.model.CostConversionRateResult;
import com.spaceboost.challenge.service.CampaignService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

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
    private static final int COST_CONVERSION_CAMPAIGN_ID = 3;
    private static final BigDecimal COST_CONVERSION_RATE = new BigDecimal(7.92d);

    @MockBean
    private CampaignService mockCampaignService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void withRightId_getCampaign_shouldReturnCampaign() throws Exception {
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

        mvc.perform(post("/campaigns")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createdCampaignInJson(campaign)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void withExistingCampaignId_createCampaign_errorThrown() throws Exception {
        Campaign campaign = new Campaign(0);
        ApiError apiError = new ApiError("CampaignId = 1");
        String errorMessage = ExceptionHandlerAdvice.ID_EXISTS_MESSAGE + apiError.getMessage();

        when(mockCampaignService.create(campaign)).thenThrow(new IdExistsException(apiError.getMessage()));

        mvc.perform(post("/campaigns")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createdCampaignInJson(campaign)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message", is(errorMessage)));
    }

    @Test
    public void withInitialValues_getWorstCostPerConversionRate_returnMessage() throws Exception {
        CostConversionRateResult costConversionRateResult = new CostConversionRateResult(COST_CONVERSION_CAMPAIGN_ID, COST_CONVERSION_RATE);

        when(mockCampaignService.getWorstCostPerConversionRate()).thenReturn(costConversionRateResult);

        mvc.perform(get("/worstCostConversionRate"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.campaignId", is(COST_CONVERSION_CAMPAIGN_ID)))
                .andExpect(jsonPath("$.costConversionRate", is(COST_CONVERSION_RATE)));
    }

    private static String createdCampaignInJson(Campaign campaign) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(campaign);
    }

}
