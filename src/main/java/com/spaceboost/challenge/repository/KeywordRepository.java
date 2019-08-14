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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class KeywordRepository implements ChallengeRepository<Keyword> {

    private static final String JSON_PATH = "classpath:entities/keywords.json";
    @Autowired
    private ResourceLoader resourceLoader;
    private Map<Integer, Keyword> storedKeyword = new ConcurrentHashMap<>();

    @Override
    @PostConstruct
    public void loadObjectsFromJson() throws IOException {
        final ObjectMapper jsonMapper = new ObjectMapper();
        final File repositoryJsonFile = resourceLoader.getResource(JSON_PATH).getFile();
        List<Keyword> loadedKeywordList = jsonMapper.readValue(repositoryJsonFile, new TypeReference<List<Keyword>>() {
        });
        loadedKeywordList.forEach(keyword -> storedKeyword.put(keyword.getId(), keyword));
    }

    @Override
    public Keyword findById(int id) {
        return storedKeyword.get(id);
    }

    @Override
    public void add(Keyword keyword) {
        storedKeyword.put(keyword.getId(), keyword);
    }

    @Override
    public List<Keyword> getAll() {
        return new ArrayList<>(storedKeyword.values());
    }

}
