package com.spaceboost.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

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
    private float cost;
}
