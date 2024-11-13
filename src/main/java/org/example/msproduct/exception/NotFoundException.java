package org.example.msproduct.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundException extends RuntimeException {
    private String code;
    public NotFoundException(String code, String message) {
        super(message);
        this.code = code;
    }
}
