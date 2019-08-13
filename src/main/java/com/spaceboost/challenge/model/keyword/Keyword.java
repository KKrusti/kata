package com.spaceboost.challenge.model.keyword;

import com.spaceboost.challenge.model.adGroup.AdGroup;
import com.spaceboost.challenge.model.campaign.Campaign;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Keyword {

    @Id
    private int id;
    private int campaignId;
    private int adGroupId;
    private int clicks;
    private int conversions;
    private Double cost;
}
