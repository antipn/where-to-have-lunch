package com.whtl.antipn.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Vote {
    private Integer userId; //possible will disappear in DB
    private Integer restaurantId;

    public Vote(Integer userId, Integer restaurantId) {
        this.userId = userId;
        this.restaurantId = restaurantId;
    }
}
