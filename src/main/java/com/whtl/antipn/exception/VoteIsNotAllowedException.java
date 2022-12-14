package com.whtl.antipn.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteIsNotAllowedException extends RuntimeException {

    private int userId;
    private String message;

}
