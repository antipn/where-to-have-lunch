package com.whtl.antipn.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Entity of restaurant by restairant id, name, address, and open/closed status")
public class RestaurantDto {

    @Schema(description = "Restaurant id", example = "11")
    private Integer id;
    @Schema(description = "Restaurant name", example = "Grand cuisine")
    private String name;
    @Schema(description = "Restaurant address", example = "Spb, Nevsky avenue house 100")
    private String address;
    @Schema(description = "Open/closed status of restaurant for visiting", example = "true or false")
    private boolean status;

    public RestaurantDto(Integer restId, String restName, String restAddress, boolean restOpen) {
        this.id = restId;
        this.name = restName;
        this.address = restAddress;
        this.status = restOpen;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
