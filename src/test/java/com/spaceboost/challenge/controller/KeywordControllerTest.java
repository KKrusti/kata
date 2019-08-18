package com.spaceboost.challenge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spaceboost.challenge.exception.AdGroupNotFoundException;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    public void withRightCombination_getKeyword_shouldReturnKeyword() throws Exception {
        Keyword keyword = new Keyword(KEYWORD_ID, CAMPAIGN_ID, ADGROUP_ID, 1, 0, 0.54);

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
        String errorMessage = ExceptionHandlerAdvice.COMBINATION_ID_ERROR_MESSAGE + apiError.getMessage();
        when(mockKeywordService.getKeywordWithCampaignAndAdGroupId(1, 1, 1)).thenThrow(new WrongIdentifiersException(apiError.getMessage()));

        mvc.perform(get("/campaigns/" + 1 + "/adGroups/" + 1 + "/keywords/" + 1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", is(errorMessage)));
    }

    @Test
    public void withOriginalData_getMostClicked_returnKeyword() throws Exception {
        //given
        Keyword mostClickedKeyword = new Keyword(27, 0, 3, 11, 5, 9.11);
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

    @Test
    public void withOriginalData_getMostConversion_returnKeyword() throws Exception {
        //given
        Keyword mostConversionKeyword = new Keyword(27, 0, 3, 11, 5, 9.11);
        when(mockKeywordService.getMostConversions()).thenReturn(mostConversionKeyword);

        mvc.perform(get("/keywords/mostConversions"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(27)))
                .andExpect(jsonPath("$.campaignId", is(0)))
                .andExpect(jsonPath("$.adGroupId", is(3)))
                .andExpect(jsonPath("$.clicks", is(11)))
                .andExpect(jsonPath("$.conversions", is(5)))
                .andExpect(jsonPath("$.cost", is(9.11)));
    }

    @Test
    public void withCorrectId_createKeyword_created() throws Exception {
        Keyword keyword = new Keyword(61, 1, 1, 0, 0, 0d);

        when(mockKeywordService.create(keyword)).thenReturn(keyword);
        mvc.perform(post("/keywords")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createdKeywordInJson(keyword)))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    public void withNonExistingAdGroup_createKeyword_errorResponse() throws Exception {
        Keyword keyword = new Keyword(62, 1, 68, 0, 0, 0d);
        ApiError apiError = new ApiError(ExceptionHandlerAdvice.AD_GROUP_NOT_FOUND + keyword.getAdGroupId());
        String errorMessage = apiError.getMessage();

        when(mockKeywordService.create(keyword)).thenThrow(new AdGroupNotFoundException(keyword.getAdGroupId()));

        mvc.perform(post("/keywords")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createdKeywordInJson(keyword)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message", is(errorMessage)));
    }

    @Test
    public void withInconsistentCampaignAndAdGroup_createKeyword_errorResponse() throws Exception {
        Keyword keyword = new Keyword(62, 1, 3, 0, 0, 0d);
        ApiError apiError = new ApiError(ExceptionHandlerAdvice.COMBINATION_ID_ERROR_MESSAGE + "CampaignId = " + keyword.getCampaignId() + " , adGroupId = " + keyword.getAdGroupId());
        String errorMessage = apiError.getMessage();

        when(mockKeywordService.create(keyword)).thenThrow(new WrongIdentifiersException("CampaignId = " + keyword.getCampaignId() + " , adGroupId = " + keyword.getAdGroupId()));

        mvc.perform(post("/keywords")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createdKeywordInJson(keyword)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message", is(errorMessage)));
    }


    private static String createdKeywordInJson(Keyword keyword) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(keyword);
    }

    //    @Test
    //    public void withNonExistingCampaign_createAdGroup_errorResponse() throws Exception {
    //        AdGroup adGroup = new AdGroup(46, 69, 0, 0, 0);
    //        ApiError apiError = new ApiError("campaign-not-found-" + adGroup.getCampaignId());
    //        String errorMessage = apiError.getMessage();
    //
    //        when(mockAdGroupService.create(adGroup)).thenThrow(new CampaignNotFoundException(adGroup.getCampaignId()));
    //
    //        mvc.perform(post("/adGroups")
    //                .contentType(MediaType.APPLICATION_JSON)
    //                .content(createdAdGroupInJson(adGroup)))
    //                .andExpect(status().isForbidden())
    //                .andExpect(jsonPath("$.message", is(errorMessage)));
    //    }
    //
    //    private static String createdAdGroupInJson(AdGroup adGroup) throws JsonProcessingException {
    //        ObjectMapper mapper = new ObjectMapper();
    //        return mapper.writeValueAsString(adGroup);
    //    }

}
