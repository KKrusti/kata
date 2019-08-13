package com.spaceboost.challenge.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spaceboost.challenge.model.Keyword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class KeywordRepository implements ChallengeRepository<Keyword> {

    @Autowired
    private ResourceLoader resourceLoader;

    private Map<Integer, Keyword> storedKeyword = new ConcurrentHashMap<>();

    @Override
    public Keyword findById(int id) {
        return null;
    }

    @Override
    public Keyword create(Keyword object) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Keyword update(Keyword object) {
        return null;
    }

    @Override
    public List<Keyword> getAll() {
        return (List<Keyword>) storedKeyword.values();
    }

    @Override
    @PostConstruct
    public void init() throws IOException {
        final ObjectMapper jsonMapper = new ObjectMapper();
        final File repositoryJsonFile = resourceLoader.getResource("classpath:entities/keywords.json").getFile();
        final List<Keyword> loadedKeywordList = jsonMapper.readValue(repositoryJsonFile, new TypeReference<List<Keyword>>() {
        });
        loadedKeywordList.forEach(keyword -> storedKeyword.put(keyword.getId(), keyword));
    }
}