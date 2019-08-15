package com.spaceboost.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Keyword {

    @Id
    @NotNull
    private int id;
    @NotNull
    private int campaignId;
    @NotNull
    private int adGroupId;
    private int clicks;
    private int conversions;
    private float cost;

}
