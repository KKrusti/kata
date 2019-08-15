package com.spaceboost.challenge.service;

import com.spaceboost.challenge.Application;
import com.spaceboost.challenge.exception.KeywordNotFoundException;
import com.spaceboost.challenge.exception.WrongIdentifiersException;
import com.spaceboost.challenge.model.Keyword;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {KeywordService.class, Application.class})
public class KeywordServiceTest {

    private static final int EXISTING_KEYWORD_ID = 3;
    private static final int NON_EXISTING_KEYWORD_ID = 99999;
    private static final int MOST_CLICKED_ID = 27;
    private static final int MOST_CONVERTED_ID = 27;

    @Autowired
    private KeywordService keywordService;

    @Test
    public void withExistingKeyword_findById_keywordFound() {
        Keyword retrievedKeyword = keywordService.getKeyword(EXISTING_KEYWORD_ID);
        Keyword expectedKeyword = new Keyword(EXISTING_KEYWORD_ID, 2, 11, 3, 1, 2.14f);

        Assertions.assertEquals(expectedKeyword, retrievedKeyword);
    }

    @Test
    public void withNonExistingKeyword_findById_exceptionThrown() {
        Assertions.assertThrows(KeywordNotFoundException.class, () -> {
            keywordService.getKeyword(NON_EXISTING_KEYWORD_ID);
        });
    }

    @Test
    public void withExistingDataInJson_getMostClick_keywordReturned() {
        Keyword keyword = keywordService.getMostClicked();

        Assertions.assertEquals(MOST_CLICKED_ID, keyword.getId());
    }

    @Test
    public void withExistingDataInJson_getMostConversions_keywordReturned() {
        Keyword keyword = keywordService.getMostConversions();

        Assertions.assertEquals(MOST_CONVERTED_ID, keyword.getId());
    }

    @Test
    public void withExistingCampaign_getKeywordsForCampaign_getList() {
        List<Keyword> keywords = keywordService.getKeywordsForCampaignId(1);

        List<Keyword> expectedkeywords = new ArrayList<>();
        expectedkeywords.add(new Keyword(0, 1, 12, 1, 0, 0.54f));
        expectedkeywords.add(new Keyword(5, 1, 8, 5, 1, 0.47f));
        expectedkeywords.add(new Keyword(14, 1, 12, 0, 0, 0.0f));
        expectedkeywords.add(new Keyword(17, 1, 13, 1, 0, 2.13f));
        expectedkeywords.add(new Keyword(22, 1, 8, 5, 1, 4.87f));
        expectedkeywords.add(new Keyword(28, 1, 13, 2, 0, 1.49f));

        Assertions.assertEquals(expectedkeywords, keywords);
        Assertions.assertTrue(keywords.size() > 0);
    }

    @Test
    public void withWrongIdentifierCombination_getKeywordWithCampaignAndAdGroupId_throwException() {
        Assertions.assertThrows(WrongIdentifiersException.class, () ->
                keywordService.getKeywordWithCampaignAndAdGroupId(1, 1, 1));
    }

    @Test
    public void withWrightIdentifierCombination_getKeywordWithCampaignAndAdGroupId_getKeyword() {
        Keyword expectedKeyword = new Keyword(0, 1, 12, 1, 0, 0.54f);

        Keyword keyword = keywordService.getKeywordWithCampaignAndAdGroupId(1, 12, 0);

        Assertions.assertEquals(expectedKeyword, keyword);
    }
}
