package com.whtl.antipn.dto;

import lombok.Data;

public class RestaurantScoreDto {
    private Integer id;
    private String name;
    private Integer score; // discuss calculated field how to link it ?

    public RestaurantScoreDto(Integer id, String name, Integer score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getScore() {
        return score;
    }
}


