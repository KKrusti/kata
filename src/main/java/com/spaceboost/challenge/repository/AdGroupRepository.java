package com.spaceboost.challenge.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spaceboost.challenge.exception.AdGroupNotFoundException;
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

    @Autowired
    private ResourceLoader resourceLoader;

    private Map<Integer, AdGroup> storedAdGroup = new ConcurrentHashMap<>();

    @Override
    public AdGroup findById(int id) {
        AdGroup adGroup = storedAdGroup.get(id);
        if (adGroup != null) {
            return adGroup;
        } else {
            throw new AdGroupNotFoundException(id);
        }
    }

    @Override
    public AdGroup create(AdGroup object) {
        return null;
    }

    @Override
    public List<AdGroup> getAll() {
        return new ArrayList<>(storedAdGroup.values());
    }

    @Override
    @PostConstruct
    public void init() throws IOException {
        final ObjectMapper jsonMapper = new ObjectMapper();
        final File repositoryJsonFile = resourceLoader.getResource("classpath:entities/adGroups.json").getFile();
        final List<AdGroup> loadedAdGroupList = jsonMapper.readValue(repositoryJsonFile, new TypeReference<List<AdGroup>>() {
        });
        loadedAdGroupList.forEach(adgroup -> storedAdGroup.put(adgroup.getId(), adgroup));
    }
}
