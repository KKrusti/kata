package com.spaceboost.challenge.repository;

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
@SpringBootTest(classes = KeywordRepository.class)
public class KeywordRepositoryTest {

    @Autowired
    private KeywordRepository keywordRepository;

    private static final int EXISTING_KEYWORD_ID = 3;
    private static final int NON_EXISTING_KEYWORD_ID = 99999;
    private static final int MOST_CLICKED_ID = 27;

    @Test
    public void withExistingKeyword_findById_keywordFound() {
        Keyword keyword = keywordRepository.findById(EXISTING_KEYWORD_ID);

        Assertions.assertNotNull(keyword);
    }

    @Test
    public void withNonExistingKeyword_findById_exceptionThrown() {
        Assertions.assertThrows(KeywordNotFoundException.class, () -> {
            keywordRepository.findById(NON_EXISTING_KEYWORD_ID);
        });
    }

    @Test
    public void withExistingDataInJson_getAll_keywordsFound() {
        List<Keyword> keywords = keywordRepository.getAll();

        Assertions.assertTrue(!keywords.isEmpty());
    }

    @Test
    public void withExistingDataInJson_getMostClick_keywordReturned() {
        Keyword keyword = keywordRepository.getMostClicked();

        Assertions.assertEquals(MOST_CLICKED_ID, keyword.getId());
    }
}
