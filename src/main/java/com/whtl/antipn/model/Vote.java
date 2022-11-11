package com.whtl.antipn.model;

import lombok.Data;

@Data
public class Vote {
    private Integer userId;
    private Integer restaurantId;

    public Vote(Integer userId, Integer restaurantId) {
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "userId=" + userId +
                ", restaurantId=" + restaurantId +
                '}';
    }
}
