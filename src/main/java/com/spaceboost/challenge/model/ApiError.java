package com.spaceboost.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Entity;

@Entity
@AllArgsConstructor
@Getter
public class ApiError {
    private String message;
}
