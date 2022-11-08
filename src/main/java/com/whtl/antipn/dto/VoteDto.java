package com.whtl.antipn.dto;

import lombok.Data;


public class VoteDto {
    private Integer userId;
    private Integer restaurantId;

    public VoteDto(Integer userId, Integer restaurantId) {
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }
}
