package com.whtl.antipn.model;

import lombok.Data;

@Data
public class Role {

    private Integer id;
    private String name;
    private String description;

    public Role(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
