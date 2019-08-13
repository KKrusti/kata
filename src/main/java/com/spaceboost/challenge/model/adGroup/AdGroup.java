package com.spaceboost.challenge.model.adGroup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AdGroup {

    @Id
    private int id;
    private int campaignId;
    private int clicks;
    private int conversions;
    private Double cost;
}
