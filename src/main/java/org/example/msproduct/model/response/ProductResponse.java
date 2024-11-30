package org.example.msproduct.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.msproduct.model.dto.Features;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal rating;
    private Boolean subscribed;
    private List<Features> features;
    private List<ProductImageResponse> images;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
