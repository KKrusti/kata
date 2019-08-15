package com.spaceboost.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AdGroup {

    @Id
    @NotNull
    private int id;
    @NotNull
    private int campaignId;
    private int clicks;
    private int conversions;
    private float cost;
}
