package com.spaceboost.challenge.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spaceboost.challenge.model.Campaign;
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
public class CampaignRepository implements ChallengeRepository<Campaign> {

    @Autowired
    private ResourceLoader resourceLoader;

    private Map<Integer, Campaign> storedCampaign = new ConcurrentHashMap<>();

    @Override
    public Campaign findById(int id) {
        return null;
    }

    @Override
    public Campaign create(Campaign object) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Campaign update(Campaign object) {
        return null;
    }

    @Override
    public List<Campaign> getAll() {
        return (List<Campaign>) storedCampaign.values();
    }

    @PostConstruct
    public void init() throws IOException {
        final ObjectMapper jsonMapper = new ObjectMapper();
        final File repositoryJsonFile = resourceLoader.getResource("classpath:entities/campaigns.json").getFile();
        final List<Campaign> loadedCampaignList = jsonMapper.readValue(repositoryJsonFile, new TypeReference<List<Campaign>>() {
        });
        loadedCampaignList.forEach(campaign -> storedCampaign.put(campaign.getId(), campaign));
    }


}

