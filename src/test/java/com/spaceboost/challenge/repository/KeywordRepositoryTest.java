package com.spaceboost.challenge.repository;

import com.spaceboost.challenge.model.Keyword;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = KeywordRepository.class)
class KeywordRepositoryTest {

    private static final int EXISTING_KEYWORD_ID = 3;
    private static final int NON_EXISTING_KEYWORD_ID = 99999;

    @Autowired
    private KeywordRepository keywordRepository;

    @Test
    void withExistingDataInJson_getAll_keywordsFound() {
        List<Keyword> keywords = keywordRepository.getAll();

        Assertions.assertTrue(keywords.size() > 0);
    }

    @Test
    void withNonExistingKeyword_findById_keywordNotFound() {
        assertThat(keywordRepository.findById(NON_EXISTING_KEYWORD_ID)).isEmpty();
    }

    @Test
    void withExistingDataInJSon_findById_keywordFound() {
        Keyword retrievedKeyword = keywordRepository.findById(EXISTING_KEYWORD_ID).get();
        Keyword expectedKeyword = new Keyword(EXISTING_KEYWORD_ID, 2, 11, 3, 1, 2.14);

        Assertions.assertEquals(expectedKeyword, retrievedKeyword);
    }

    @Test
    void withExistingDataInJSon_findByNonExistingId_keywordNotFound() {
        assertThat(keywordRepository.findById(NON_EXISTING_KEYWORD_ID)).isEmpty();
    }

}
