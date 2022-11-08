package com.whtl.antipn.dto;

public class RestaurantDto {

    private Integer id;
    private String name;
    private String address;
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
