package org.example.msproduct.model.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorConstants {
    PRODUCT_NOT_FOUND_EXCEPTION("PRODUCT_NOT_FOUND", "Product Not Found"),
    CLIENT_EXCEPTION("CLIENT", "Client Exception"),
    UNEXPECTED_EXCEPTION("UNEXPECTED", "Unexpected Exception"),
    QUEUE_EXCEPTION("QUEUE", "Queue Exception"),
    PRODUCT_QUANTITY_EXCEPTION("PRODUCT_QUANTITY", "Product Quantity Less than Sold");
    private final String code;
    private final String message;
}
