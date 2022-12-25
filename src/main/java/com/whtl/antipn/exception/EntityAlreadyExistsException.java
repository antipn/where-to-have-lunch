package com.whtl.antipn.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntityAlreadyExistsException extends RuntimeException {
    private String entityName;
    private String message;

}

