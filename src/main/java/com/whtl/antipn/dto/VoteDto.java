package com.whtl.antipn.dto;

import com.whtl.antipn.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Entity for showing user choose for visiting restaurant on date")
public class VoteDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "User id", example = "1")
    private Integer user;
    @Schema(description = "Restaurant id as user's choose")
    private Integer restaurant;

}
