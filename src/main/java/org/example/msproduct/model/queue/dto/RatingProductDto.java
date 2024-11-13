package org.example.msproduct.model.queue.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingProductDto {
    private Long productId;
    private Integer ratingCount;
    private Double ratingAverage;
}
