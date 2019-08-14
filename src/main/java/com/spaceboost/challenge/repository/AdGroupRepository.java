package com.spaceboost.challenge.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spaceboost.challenge.model.AdGroup;
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
public class AdGroupRepository implements ChallengeRepository<AdGroup> {

    private static final String JSON_PATH = "classpath:entities/adGroups.json";
    @Autowired
    private ResourceLoader resourceLoader;
    private Map<Integer, AdGroup> storedAdGroup = new ConcurrentHashMap<>();

    @Override
    @PostConstruct
    public void init() throws IOException {
        final ObjectMapper jsonMapper = new ObjectMapper();
        final File repositoryJsonFile = resourceLoader.getResource(JSON_PATH).getFile();
        List<AdGroup> loadedAdGroupList = jsonMapper.readValue(repositoryJsonFile, new TypeReference<List<AdGroup>>() {
        });
        loadedAdGroupList.forEach(adgroup -> storedAdGroup.put(adgroup.getId(), adgroup));
    }

    @Override
    public AdGroup findById(int id) {
        return storedAdGroup.get(id);
    }

    @Override
    public AdGroup create(AdGroup object) {
        return null;
    }

    @Override
    public List<AdGroup> getAll() {
        return new ArrayList<>(storedAdGroup.values());
    }

}
