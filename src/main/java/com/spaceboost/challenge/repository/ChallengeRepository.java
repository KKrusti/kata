package com.spaceboost.challenge.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChallengeRepository<T>{

    Optional<T> findById(int id);

    T create(T object);

    boolean delete(int id);

    T update(T object);
}
