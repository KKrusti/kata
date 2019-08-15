package com.spaceboost.challenge.service;

import com.spaceboost.challenge.Application;
import com.spaceboost.challenge.exception.KeywordNotFoundException;
import com.spaceboost.challenge.model.Keyword;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

//        List<Keyword> expectedAdGroups = new ArrayList<>();
//        expectedAdGroups.add(new Keyword(8, 1, 5, 0, 3.32f));
//        expectedAdGroups.add(new Keyword(12, 1, 87, 13, 101.73f));
//        expectedAdGroups.add(new Keyword(13, 1, 55, 18, 99.47f));

//        Assertions.assertEquals(expectedAdGroups, keywords);
        Assertions.assertTrue(keywords.size() > 0);
    }

}
