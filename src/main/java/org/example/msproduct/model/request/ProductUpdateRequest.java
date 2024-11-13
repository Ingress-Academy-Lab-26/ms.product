package org.example.msproduct.model.request;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Builder
public class ProductUpdateRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private Long categoryId;
    private Map<String, String> features;
    private List<ProductImageRequest> images;
}
