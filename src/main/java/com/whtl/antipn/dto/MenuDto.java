package com.whtl.antipn.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Entity of menu by dish name and dish price")
public class MenuDto {

    @Schema(description = "Name of dish", example = "Salmon fist plate")
    private String name;
    @Schema(description = "Price for dish", example = "100.5")
    private Double price;


    public MenuDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
