package com.whtl.antipn.model;

import lombok.Data;

@Data
public class Menu {
    private String dishName;
    private Double dishPrice;

    public Menu(String dishName, Double dishPrice) {
        this.dishName = dishName;
        this.dishPrice = dishPrice;
    }
}
