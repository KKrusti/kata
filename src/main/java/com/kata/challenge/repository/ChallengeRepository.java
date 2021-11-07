package com.kata.challenge.repository;

import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChallengeRepository<T> {

    Optional<T> findById(int id);

    T add(T object);

    List<T> getAll();

    @PostConstruct
    void loadObjectsFromJson() throws IOException;

}
