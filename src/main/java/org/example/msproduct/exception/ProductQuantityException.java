package org.example.msproduct.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ProductQuantityException extends RuntimeException{
    private final String code;
    public ProductQuantityException(String code, String message){
        super(message);
        this.code = code;
    }
}
