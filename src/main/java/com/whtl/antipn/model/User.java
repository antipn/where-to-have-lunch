package com.whtl.antipn.model;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private Integer id;
    private List<Role> roles;

    public User(Integer id, List<Role> roles) {
        this.id = id;
        this.roles = roles;
    }
}
