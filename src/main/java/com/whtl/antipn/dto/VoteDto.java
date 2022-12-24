package com.whtl.antipn.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Entity for showing user choose for visiting restaurant on date")
public class VoteDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "User id", example = "1")
    private Integer user_id;
    @Schema(description = "Restaurant id as user's choose")
    private Integer restaurant_id;

    //отдавать в голосе название ресторана
    //хранить не id а сам ресторан

    public VoteDto(Integer userId, Integer restaurantId) {
        this.user_id = userId;
        this.restaurant_id = restaurantId;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Integer restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

}
