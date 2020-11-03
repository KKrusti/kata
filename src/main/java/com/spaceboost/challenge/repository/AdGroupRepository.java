package com.spaceboost.challenge.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spaceboost.challenge.model.AdGroup;
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
public class AdGroupRepository implements ChallengeRepository<AdGroup> {

    @Autowired
    private ResourceLoader resourceLoader;

    private static final String JSON_PATH = "classpath:entities/adGroups.json";
    private Map<Integer, AdGroup> storedAdGroup = new ConcurrentHashMap<>();

    @PostConstruct
    @Override
    public void loadObjectsFromJson() {
        try (Reader reader = new FileReader(resourceLoader.getResource(JSON_PATH).getFile())) {
            Type listType = new TypeToken<ArrayList<AdGroup>>() {
            }.getType();
            List<AdGroup> loadedAdGroups = new Gson().fromJson(reader, listType);
            loadedAdGroups.forEach(adGroup -> storedAdGroup.put(adGroup.getId(), adGroup));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @PostConstruct
//    @Override
//    public void loadObjectsFromJson() {
//        List<AdGroup> test = new ArrayList<>();
//        test = loadObjectsFromJson(JSON_PATH);
//        test.forEach(adGroup -> storedAdGroup.put(adGroup.getId(), adGroup));
//    }

    @Override
    public AdGroup findById(int id) {
        return storedAdGroup.get(id);
    }

    @Override
    public AdGroup add(AdGroup adGroup) {
        storedAdGroup.put(adGroup.getId(), adGroup);
        return adGroup;
    }

    @Override
    public List<AdGroup> getAll() {
        return new ArrayList<>(storedAdGroup.values());
    }

}
