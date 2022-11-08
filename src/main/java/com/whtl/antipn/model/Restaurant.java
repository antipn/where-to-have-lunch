package com.whtl.antipn.model;

import lombok.Data;

@Data
public class Restaurant {
    private Integer id;
    private String name;
    private String address;
    private boolean status;

    public Restaurant(Integer id, String name, String address, boolean status) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.status = status;
    }
}
