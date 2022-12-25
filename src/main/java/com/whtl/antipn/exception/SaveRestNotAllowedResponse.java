package com.whtl.antipn.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveRestNotAllowedResponse {

    private int status;
    private String message;
    private long timestamp;

}
