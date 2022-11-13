package com.whtl.antipn.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Entity of menu by dish name and dish price")
public class MenuDto {
    @Schema(description = "Name of dish", example = "Salmon fist plate")
    private String dishName;
    @Schema(description = "Price for dish", example = "100.5")
    private Double dishPrice;

    public MenuDto(String dishName, Double dishPrice) {
        this.dishName = dishName;
        this.dishPrice = dishPrice;
    }

    public MenuDto() {
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public Double getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(Double dishPrice) {
        this.dishPrice = dishPrice;
    }
}
