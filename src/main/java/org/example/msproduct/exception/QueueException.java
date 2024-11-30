package org.example.msproduct.exception;

import lombok.Getter;

@Getter
public class QueueException extends RuntimeException {
    private final String code;
    public QueueException(String code, String message) {
        super(message);
        this.code = code;
    }
}
