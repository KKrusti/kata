package com.spaceboost.challenge.controller;

import com.spaceboost.challenge.exception.ExceptionHandlerAdvice;
import com.spaceboost.challenge.exception.WrongIdentifiersException;
import com.spaceboost.challenge.model.ApiError;
import com.spaceboost.challenge.model.Keyword;
import com.spaceboost.challenge.service.KeywordService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(KeywordController.class)
public class KeywordControllerTest {

    private static final int CAMPAIGN_ID = 1;
    private static final int ADGROUP_ID = 12;
    private static final int KEYWORD_ID = 0;

    @MockBean
    private KeywordService mockKeywordService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void withWrightCombination_getKeyword_shouldReturnKeyword() throws Exception {
        Keyword keyword = new Keyword(KEYWORD_ID, CAMPAIGN_ID, ADGROUP_ID, 1, 0, 0.54f);

        when(mockKeywordService.getKeywordWithCampaignAndAdGroupId(1, 12, 0)).thenReturn(keyword);

        mvc.perform(get("/campaigns/" + CAMPAIGN_ID + "/adGroups/" + ADGROUP_ID + "/keywords/" + KEYWORD_ID))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(KEYWORD_ID)))
                .andExpect(jsonPath("$.campaignId", is(CAMPAIGN_ID)))
                .andExpect(jsonPath("$.adGroupId", is(ADGROUP_ID)))
                .andExpect(jsonPath("$.clicks", is(1)))
                .andExpect(jsonPath("$.conversions", is(0)))
                .andExpect(jsonPath("$.cost", is(0.54)));
    }

    @Test
    public void withWrongCombination_getKeyword_shouldReturnError() throws Exception {
        ApiError apiError = new ApiError("CampaignId = 1 , adGroupId = 1 , KeywordId = 1");
        String errorMessage = ExceptionHandlerAdvice.ERROR_MESSAGE + apiError.getMessage();
        when(mockKeywordService.getKeywordWithCampaignAndAdGroupId(1, 1, 1)).thenThrow(new WrongIdentifiersException(apiError.getMessage()));

        mvc.perform(get("/campaigns/" + 1 + "/adGroups/" + 1 + "/keywords/" + 1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", is(errorMessage)));
    }

    @Test
    public void withOriginalData_getMostClicked_returnKeyword() throws Exception {
        //given
        Keyword mostClickedKeyword = new Keyword(27, 0, 3, 11, 5, 9.11f);
        when(mockKeywordService.getMostClicked()).thenReturn(mostClickedKeyword);

        mvc.perform(get("/keywords/mostClicked"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(27)))
                .andExpect(jsonPath("$.campaignId", is(0)))
                .andExpect(jsonPath("$.adGroupId", is(3)))
                .andExpect(jsonPath("$.clicks", is(11)))
                .andExpect(jsonPath("$.conversions", is(5)))
                .andExpect(jsonPath("$.cost", is(9.11)));
    }
}
