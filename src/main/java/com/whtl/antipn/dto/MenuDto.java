package com.whtl.antipn.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Entity of menu by dish name and dish price")
public class MenuDto {

    @Schema(description = "Date of menu's position", example = "2023-01-06")
    private LocalDate date;
    @Schema(description = "Restaurant id", example = "1003")
    private Integer restaurant;
    @Schema(description = "Name of dish", example = "Salmon fist plate")
    private String name;
    @Schema(description = "Price for dish", example = "100.5")
    private Double price;

    @Override
    public String toString() {
        return "MenuDto{" +
                "date=" + date +
                ", restaurant=" + restaurant +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
