package com.spaceboost.challenge.service;

import com.spaceboost.challenge.exception.KeywordNotFoundException;
import com.spaceboost.challenge.model.Keyword;
import com.spaceboost.challenge.repository.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class KeywordService {

    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    public KeywordService(KeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }

    public Keyword getKeyword(int keywordId) {
        Keyword keyword = keywordRepository.findById(keywordId);
        if (keyword != null) {
            return keyword;
        } else {
            throw new KeywordNotFoundException(keywordId);
        }
    }

    public Keyword getMostClicked() {
        return keywordRepository.getAll().stream().max(Comparator.comparing(Keyword::getClicks)).orElseThrow(() -> new IllegalArgumentException());
    }

    public Keyword getMostConversions() {
        return keywordRepository.getAll().stream().max(Comparator.comparing(Keyword::getConversions)).orElseThrow(() -> new IllegalArgumentException());
    }
}
