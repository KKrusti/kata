package com.spaceboost.challenge.repository;

import com.spaceboost.challenge.model.Keyword;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = KeywordRepository.class)
public class KeywordRepositoryTest {

    private static final int EXISTING_KEYWORD_ID = 3;
    private static final int NON_EXISTING_KEYWORD_ID = 99999;

    @Autowired
    private KeywordRepository keywordRepository;

    @Test
    public void withExistingDataInJson_getAll_keywordsFound() {
        List<Keyword> keywords = keywordRepository.getAll();

        Assertions.assertTrue(keywords.size() > 0);
    }

    @Test
    public void withExistingDataInJSon_findById_keywordFound() {
        Keyword retrievedKeyword = keywordRepository.findById(EXISTING_KEYWORD_ID);
        Keyword expectedKeyword = new Keyword(EXISTING_KEYWORD_ID, 2, 11, 3, 1, 2.14f);

        Assertions.assertEquals(expectedKeyword, retrievedKeyword);
    }

    @Test
    public void withExistingDataInJSon_findById_keywordNotFound() {
        Keyword retrievedKeyword = keywordRepository.findById(NON_EXISTING_KEYWORD_ID);

        Assertions.assertNull(retrievedKeyword);
    }

    @Test
    public void withNewKeyword_add_keywordCreated() {
        int previousSize = keywordRepository.getAll().size();
        Keyword keyword = new Keyword(99, 99, 99, 99, 50, 50.00f);
        keywordRepository.add(keyword);
        int postSize = keywordRepository.getAll().size();
        Assertions.assertTrue(previousSize < postSize);
    }
}
