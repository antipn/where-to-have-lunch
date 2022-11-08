package com.whtl.antipn.dto;

import lombok.Data;


public class MenuDto {
    private String dishName;
    private Double dishPrice;

    public MenuDto(String dishName, Double dishPrice) {
        this.dishName = dishName;
        this.dishPrice = dishPrice;
    }

    public String getDishNaming() {
        return dishName;
    }

    public void setDishNaming(String dishName) {
        this.dishName = dishName;
    }

    public Double getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(Double dishPrice) {
        this.dishPrice = dishPrice;
    }
}
