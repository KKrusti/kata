package com.kata.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Keyword {

    @Id
    private int id;
    private int campaignId;
    private int adGroupId;
    private int clicks;
    private int conversions;
    private Double cost;

}
