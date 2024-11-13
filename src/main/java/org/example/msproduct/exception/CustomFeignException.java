package org.example.msproduct.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
public class CustomFeignException extends RuntimeException{
    private final int statusCode;
    private final String code;
    public CustomFeignException(String code, String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
        this.code = code;
    }
}
