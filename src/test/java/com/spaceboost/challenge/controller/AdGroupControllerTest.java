package com.spaceboost.challenge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spaceboost.challenge.exception.CampaignNotFoundException;
import com.spaceboost.challenge.exception.ExceptionHandlerAdvice;
import com.spaceboost.challenge.exception.WrongIdentifiersException;
import com.spaceboost.challenge.model.AdGroup;
import com.spaceboost.challenge.model.ApiError;
import com.spaceboost.challenge.service.AdGroupService;
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
@WebMvcTest(AdGroupController.class)
public class AdGroupControllerTest {

    private static final int CAMPAIGN_ID = 4;
    private static final int ADGROUP_ID = 1;

    @MockBean
    private AdGroupService mockAdGroupService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void withRightCombination_getAdGroup_shouldReturnadGroup() throws Exception {
        AdGroup adGroup = new AdGroup(ADGROUP_ID, CAMPAIGN_ID, 83, 17, 1.43d);

        when(mockAdGroupService.getAdGroupWithCampaign(CAMPAIGN_ID, ADGROUP_ID)).thenReturn(adGroup);

        mvc.perform(get("/campaigns/" + CAMPAIGN_ID + "/adGroups/" + ADGROUP_ID))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(ADGROUP_ID)))
                .andExpect(jsonPath("$.campaignId", is(CAMPAIGN_ID)))
                .andExpect(jsonPath("$.clicks", is(83)))
                .andExpect(jsonPath("$.conversions", is(17)))
                .andExpect(jsonPath("$.cost", is(1.43)));
    }

    @Test
    public void withWrongCombination_getAdgroup_shouldReturnError() throws Exception {
        ApiError apiError = new ApiError("CampaignId = 1 , adGroupId = 1");
        String errorMessage = ExceptionHandlerAdvice.COMBINATION_ID_ERROR_MESSAGE + apiError.getMessage();
        when(mockAdGroupService.getAdGroupWithCampaign(1, ADGROUP_ID)).thenThrow(new WrongIdentifiersException(apiError.getMessage()));

        mvc.perform(get("/campaigns/" + 1 + "/adGroups/" + ADGROUP_ID))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", is(errorMessage)));
    }

    @Test
    public void withCorrectId_createAdGroup_created() throws Exception {
        AdGroup adGroup = new AdGroup(45, 1, 0, 0, 0d);
        when(mockAdGroupService.create(adGroup)).thenReturn(adGroup);

        mvc.perform(post("/adGroups")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createdAdGroupInJson(adGroup)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void withNonExistingCampaign_createAdGroup_errorResponse() throws Exception {
        AdGroup adGroup = new AdGroup(46, 69, 0, 0, 0d);
        ApiError apiError = new ApiError(ExceptionHandlerAdvice.CAMPAIGN_NOT_FOUND + adGroup.getCampaignId());
        String errorMessage = apiError.getMessage();

        when(mockAdGroupService.create(adGroup)).thenThrow(new CampaignNotFoundException(adGroup.getCampaignId()));

        mvc.perform(post("/adGroups")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createdAdGroupInJson(adGroup)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message", is(errorMessage)));
    }

    private static String createdAdGroupInJson(AdGroup adGroup) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(adGroup);
    }

}