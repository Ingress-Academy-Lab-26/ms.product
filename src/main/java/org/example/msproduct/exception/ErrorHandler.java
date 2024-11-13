package org.example.msproduct.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.example.msproduct.constant.ErrorConstants.UNEXPECTED_EXCEPTION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("ErrorLog.UnhandledException: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().body(ErrorResponse.builder()
                .code(UNEXPECTED_EXCEPTION.getCode())
                .message(UNEXPECTED_EXCEPTION.getMessage())
                .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(NotFoundException ex) {
        log.error("ErrorLog.NotFoundException: {}", ex.getMessage(), ex);
        return ResponseEntity.status(BAD_REQUEST).body(ErrorResponse.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleExceptions(MethodArgumentNotValidException ex) {
        log.error("ErrorLog.MethodArgumentNotValidException: {}", ex.getMessage(), ex);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(CustomFeignException.class)
    public ResponseEntity<ErrorResponse> handleException(CustomFeignException ex) {
        log.error("ErrorLog.CustomFeignException: {}", ex.getMessage(), ex);
        return ResponseEntity.status(ex.getStatusCode()).body(
                ErrorResponse.builder()
                        .message(ex.getMessage())
                        .code(ex.getCode())
                        .build()
        );
    }

    @ExceptionHandler(QueueException.class)
    public ResponseEntity<ErrorResponse> handleException(QueueException ex) {
        log.error("ErrorLog.QueueException: {}", ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(
                ErrorResponse.builder()
                        .message(ex.getMessage())
                        .code(ex.getCode())
                        .build()
        );
    }

    @ExceptionHandler(ProductQuantityException.class)
    public ResponseEntity<ErrorResponse> handleException(ProductQuantityException ex) {
        log.error("ErrorLog.ProductQuantityException: {}", ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(
                ErrorResponse.builder()
                        .message(ex.getMessage())
                        .code(ex.getCode())
                        .build()
        );
    }

}
