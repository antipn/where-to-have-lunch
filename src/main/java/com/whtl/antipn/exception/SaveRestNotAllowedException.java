package com.whtl.antipn.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveRestNotAllowedException extends RuntimeException {

    private String message;

}
