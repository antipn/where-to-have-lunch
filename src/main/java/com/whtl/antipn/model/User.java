package com.whtl.antipn.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class User {
    private Integer id;
    private String email;
    private String password;
    private boolean enabled = true;
    private Date registered = new Date();
    private List<Role> roles;

    public User(Integer id, String email, String password, boolean enabled, List<Role> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }
}
