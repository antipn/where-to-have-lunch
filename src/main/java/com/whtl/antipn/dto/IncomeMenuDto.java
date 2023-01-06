package com.whtl.antipn.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Entity for incoming menu with only dish name and dish price")
public class IncomeMenuDto {

    @Schema(description = "Name of dish", example = "Salmon fist plate")
    private String name;
    @Schema(description = "Price for dish", example = "100.5")
    private Double price;
}
