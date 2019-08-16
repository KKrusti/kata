package com.spaceboost.challenge.repository;

import com.spaceboost.challenge.exception.IdExistsException;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Repository
public interface ChallengeRepository<T> {

    T findById(int id);

    T add(T object) throws IdExistsException;

    List<T> getAll();

    @PostConstruct
    void loadObjectsFromJson() throws IOException;

}
