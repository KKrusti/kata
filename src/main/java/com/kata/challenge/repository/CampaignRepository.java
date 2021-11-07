package com.kata.challenge.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kata.challenge.model.Campaign;
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
public class CampaignRepository implements ChallengeRepository<Campaign> {

    private static final String JSON_PATH = "classpath:entities/campaigns.json";
    @Autowired
    private ResourceLoader resourceLoader;
    private Map<Integer, Campaign> storedCampaign = new ConcurrentHashMap<>();

    @PostConstruct
    @Override
    public void loadObjectsFromJson() throws IOException {
        final ObjectMapper jsonMapper = new ObjectMapper();
        final File repositoryJsonFile = resourceLoader.getResource(JSON_PATH).getFile();
        List<Campaign> loadedCampaignList = jsonMapper.readValue(repositoryJsonFile, new TypeReference<List<Campaign>>() {
        });
        loadedCampaignList.forEach(campaign -> storedCampaign.put(campaign.getId(), campaign));
    }

    @Override
    public Campaign findById(int id) {
        return storedCampaign.get(id);
    }

    @Override
    public Campaign add(Campaign campaign) {
        storedCampaign.put(campaign.getId(), campaign);
        return campaign;
    }

    @Override
    public List<Campaign> getAll() {
        return new ArrayList<>(storedCampaign.values());
    }

}

