package com.whtl.antipn.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Entity for showing user choose for visiting restaurant on date")
public class VoteDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "User id", example = "1")
    private Integer userId;
    @Schema(description = "Restaurant id as user's choose")
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
