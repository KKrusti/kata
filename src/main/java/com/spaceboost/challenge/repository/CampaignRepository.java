package com.spaceboost.challenge.repository;

import com.spaceboost.challenge.model.campaign.Campaign;

import java.util.Optional;

public class CampaignRepository implements ChallengeRepository<Campaign> {

    @Override
    public Optional<Campaign> findById(int id) {
        return Optional.empty();
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



}

