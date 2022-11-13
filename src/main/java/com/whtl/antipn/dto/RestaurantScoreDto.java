package com.whtl.antipn.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Entity for showing rating")
public class RestaurantScoreDto {
    @Schema(description = "Restaurant id", example = "11")
    private Integer id;
    @Schema(description = "Restaurant name", example = "Grand cuisine")
    private String name;
    @Schema(description = "Restaurant score by users choosing on rating date", example = "92")
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


