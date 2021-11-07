package com.kata.challenge.service;

import com.kata.challenge.Application;
import com.kata.challenge.exception.AdGroupNotFoundException;
import com.kata.challenge.exception.IdExistsException;
import com.kata.challenge.exception.KeywordNotFoundException;
import com.kata.challenge.exception.WrongIdentifiersException;
import com.kata.challenge.model.CostPerCampaign;
import com.kata.challenge.model.Keyword;
import com.kata.challenge.repository.KeywordRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {KeywordService.class, Application.class})
public class KeywordServiceTest {

    private static final int EXISTING_KEYWORD_ID = 3;
    private static final int NON_EXISTING_KEYWORD_ID = 99999;
    private static final int MOST_CLICKED_ID = 27;
    private static final int MOST_CONVERTED_ID = 27;
    private static final int INITIAL_KEYWORD_NUMBER = 30;

    @Autowired
    private KeywordService keywordService;

    @Autowired
    private KeywordRepository repository;

    @Test
    public void withInitialStatus_getAll() {
        List<Keyword> keywords = keywordService.getAll();

        Assertions.assertEquals(repository.getAll().size(), keywords.size());
    }

    @Test
    public void withExistingKeyword_findById_keywordFound() {
        Keyword retrievedKeyword = keywordService.getKeyword(EXISTING_KEYWORD_ID);
        Keyword expectedKeyword = new Keyword(EXISTING_KEYWORD_ID, 2, 11, 3, 1, 2.14d);

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

        List<Keyword> expectedKeywords = new ArrayList<>();
        expectedKeywords.add(new Keyword(0, 1, 12, 1, 0, 0.54d));
        expectedKeywords.add(new Keyword(5, 1, 8, 5, 1, 0.47d));
        expectedKeywords.add(new Keyword(14, 1, 12, 0, 0, 0.0d));
        expectedKeywords.add(new Keyword(17, 1, 13, 1, 0, 2.13d));
        expectedKeywords.add(new Keyword(22, 1, 8, 5, 1, 4.87d));
        expectedKeywords.add(new Keyword(28, 1, 13, 2, 0, 1.49d));

        Assertions.assertEquals(expectedKeywords, keywords);
        Assertions.assertTrue(keywords.size() > 0);
    }

    @Test
    public void withWrongIdentifierCombination_getKeywordWithCampaignAndAdGroupId_throwException() {
        Assertions.assertThrows(WrongIdentifiersException.class, () ->
                keywordService.getKeywordWithCampaignAndAdGroupId(1, 1, 1));
    }

    @Test
    public void withRightIdentifierCombination_getKeywordWithCampaignAndAdGroupId_getKeyword() {
        Keyword expectedKeyword = new Keyword(0, 1, 12, 1, 0, 0.54d);

        Keyword keyword = keywordService.getKeywordWithCampaignAndAdGroupId(1, 12, 0);

        Assertions.assertEquals(expectedKeyword, keyword);
    }

    @Test
    public void withNewKeyword_create_keywordCreated() {
        int previousSize = keywordService.getAll().size();
        Keyword keyword = new Keyword(99, 2, 6, 2, 0, 0d);
        keywordService.create(keyword);
        int postSize = keywordService.getAll().size();
        Assertions.assertTrue(previousSize < postSize);
    }

    @Test
    public void withDuplicatedKeyword_create_errorThrown() {
        Keyword keyword = new Keyword(0, 0, 0, 0, 0, 0d);
        Assertions.assertThrows(IdExistsException.class, () -> keywordService.create(keyword));
    }

    @Test
    public void withNotExistingAdGroup_create_errorThrown() {
        Keyword keyword = new Keyword(666, 2, 999, 2, 50, 50.00d);
        Assertions.assertThrows(AdGroupNotFoundException.class, () -> keywordService.create(keyword));
    }

    @Test
    public void withExistingAdGroupWrongCampaign_create_errorThrown() {
        Keyword keyword = new Keyword(111, 1, 6, 2, 50, 50.00d);
        Assertions.assertThrows(WrongIdentifiersException.class, () -> keywordService.create(keyword));
    }

    @Test
    public void withExistingAdGroupRightCampaign_create_keywordCreated() {
        Keyword keyword = new Keyword(123, 2, 6, 0, 0, 0d);
        int previousSize = keywordService.getAll().size();
        keywordService.create(keyword);
        int postSize = keywordService.getAll().size();

        Assertions.assertTrue(previousSize < postSize);
    }

    @Test
    public void withDefaultValues_getCostPerCampaign_correctValues() {
        Map<Integer, CostPerCampaign> costPerCampaignMap = keywordService.getCostPerCampaign();

        CostPerCampaign expectedCampaign0 = new CostPerCampaign(47.44, 9.0);
        CostPerCampaign expectedCampaign1 = new CostPerCampaign(9.5, 2.0);
        CostPerCampaign expectedCampaign2 = new CostPerCampaign(7.01, 6.0);
        CostPerCampaign expectedCampaign3 = new CostPerCampaign(11.69, 1.0);
        CostPerCampaign expectedCampaign4 = new CostPerCampaign(11.19, 2.0);

        Assertions.assertEquals(expectedCampaign0, costPerCampaignMap.get(0));
        Assertions.assertEquals(expectedCampaign1, costPerCampaignMap.get(1));
        Assertions.assertEquals(expectedCampaign2, costPerCampaignMap.get(2));
        Assertions.assertEquals(expectedCampaign3, costPerCampaignMap.get(3));
        Assertions.assertEquals(expectedCampaign4, costPerCampaignMap.get(4));
    }

}
