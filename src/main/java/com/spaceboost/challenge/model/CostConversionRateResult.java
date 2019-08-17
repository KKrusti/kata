package com.spaceboost.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@Data
public class CostConversionRateResult {

    private int campaignId;
    private BigDecimal costConversionRate;
}
