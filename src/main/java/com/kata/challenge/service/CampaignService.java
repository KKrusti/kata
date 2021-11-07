package com.kata.challenge.service;

import com.kata.challenge.exception.CampaignNotFoundException;
import com.kata.challenge.exception.IdExistsException;
import com.kata.challenge.model.Campaign;
import com.kata.challenge.model.CostConversionRateResult;
import com.kata.challenge.model.CostPerCampaign;
import com.kata.challenge.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CampaignService {

    private CampaignRepository campaignRepository;

    @Autowired
    private KeywordService keywordService;

    @Autowired
    private AdGroupService adGroupService;

    @Autowired
    public CampaignService(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    public Campaign getCampaign(int campaignId) throws CampaignNotFoundException {
        return campaignRepository.findById(campaignId).orElseThrow(() -> new CampaignNotFoundException(campaignId));
    }

    public List<Campaign> getAll() {
        return campaignRepository.getAll();
    }

    public Campaign create(Campaign campaign) {
        campaignIsNew(campaign.getId());
        return campaignRepository.add(campaign);
    }

    public CostConversionRateResult getWorstCostPerConversionRate() {

        Map<Integer, Double> costConversionRate = new HashMap<>();
        Stream.concat(adGroupService.getCostPerCampaign().entrySet().stream(), keywordService.getCostPerCampaign().entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (value1, value2) -> new CostPerCampaign((value1.getCost() + value2.getCost()), (value1.getConversions() + value2.getConversions()))))
                .forEach((key, costPerCampaign) ->
                        costConversionRate.put(key, costPerCampaign.getCost() / costPerCampaign.getConversions()));

        Map.Entry<Integer, Double> worstCostPerConversionRate = costConversionRate.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get();

        return new CostConversionRateResult(worstCostPerConversionRate.getKey(), BigDecimal.valueOf(worstCostPerConversionRate.getValue()).setScale(2, RoundingMode.CEILING));
    }

    private void campaignIsNew(int campaignId) {
        if (campaignRepository.findById(campaignId).isPresent()) {
            throw new IdExistsException("Campaign with id " + campaignId + " already exists");
        }
    }

}
