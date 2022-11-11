package com.whtl.antipn.dto.input;

public class VoteDtoIncome {

    private int restaurantId;

    public VoteDtoIncome(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public VoteDtoIncome() {
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
}
