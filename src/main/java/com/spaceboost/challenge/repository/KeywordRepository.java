package com.spaceboost.challenge.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spaceboost.challenge.model.Keyword;
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
public class KeywordRepository implements ChallengeRepository<Keyword> {

    @Autowired
    private ResourceLoader resourceLoader;

    private static final String JSON_PATH = "classpath:entities/keywords.json";
    private Map<Integer, Keyword> storedKeyword = new ConcurrentHashMap<>();

    @PostConstruct
    @Override
    public void loadObjectsFromJson() {
        try (Reader reader = new FileReader(resourceLoader.getResource(JSON_PATH).getFile())) {
            Type listType = new TypeToken<ArrayList<Keyword>>() {
            }.getType();
            List<Keyword> loadedKeywords = new Gson().fromJson(reader, listType);
            loadedKeywords.forEach(keyword -> storedKeyword.put(keyword.getId(), keyword));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Keyword findById(int id) {
        return storedKeyword.get(id);
    }

    @Override
    public Keyword add(Keyword keyword) {
        storedKeyword.put(keyword.getId(), keyword);
        return keyword;
    }

    @Override
    public List<Keyword> getAll() {
        return new ArrayList<>(storedKeyword.values());
    }

}
