package com.spaceboost.challenge.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spaceboost.challenge.model.Campaign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CampaignRepository implements ChallengeRepository<Campaign> {

    @Autowired
    private ResourceLoader resourceLoader;
    private static final String JSON_PATH = "classpath:entities/campaigns.json";
    private Map<Integer, Campaign> storedCampaign = new ConcurrentHashMap<>();

    @PostConstruct
    @Override
    public void loadObjectsFromJson() {
        try (Reader reader = new FileReader(resourceLoader.getResource(JSON_PATH).getFile())) {
            Type listType = new TypeToken<ArrayList<Campaign>>() {
            }.getType();
            List<Campaign> loadedCampaigns = new Gson().fromJson(reader, listType);
            loadedCampaigns.forEach(campaign -> storedCampaign.put(campaign.getId(), campaign));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

