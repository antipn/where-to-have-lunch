package com.whtl.antipn.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entity of restaurant by restairant id, name, address, and open/closed status")
public class RestaurantDto {

    @Schema(description = "Restaurant id", example = "11")
    private Integer id;
    @Schema(description = "Restaurant name", example = "Grand cuisine")
    private String name;
    @Schema(description = "Restaurant address", example = "Spb, Nevsky avenue house 100")
    private String address;
    @Schema(description = "Open/closed status of restaurant for visiting", example = "true or false")
    private boolean open;


    @Override
    public String toString() {
        return "RestaurantDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", open=" + open +
                '}';
    }
}


