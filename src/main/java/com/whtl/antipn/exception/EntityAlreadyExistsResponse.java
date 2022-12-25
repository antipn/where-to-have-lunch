package com.whtl.antipn.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntityAlreadyExistsResponse {
    private int status;
    private String message;
    private long timestamp;

}
