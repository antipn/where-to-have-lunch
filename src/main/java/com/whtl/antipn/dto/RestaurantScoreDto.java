package com.whtl.antipn.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Entity for showing rating")
public class RestaurantScoreDto {
    @Schema(description = "Restaurant id", example = "11")
    private Integer id;
    @Schema(description = "Restaurant name", example = "Grand cuisine")
    private String name;
    @Schema(description = "Restaurant score by users choosing on rating date", example = "92")
    private Integer score; // discuss calculated field how to link it ?

}


