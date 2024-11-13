package org.example.msproduct.criteria;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.USE_DEFAULTS;
import static org.example.msproduct.constant.CriteriaConstants.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCriteria {
    private String name;
    private String description;
    private Long categoryId;
    @JsonInclude(USE_DEFAULTS)
    private BigDecimal minPrice = PRICE_MIN_DEFAULT_VALUE;
    @JsonInclude(USE_DEFAULTS)
    private BigDecimal maxPrice = PRICE_MAX_DEFAULT_VALUE;
    @JsonInclude(USE_DEFAULTS)
    private Double minRating = RATING_MIN_DEFAULT_VALUE;
    @JsonInclude(USE_DEFAULTS)
    private Double maxRating = RATING_MAX_DEFAULT_VALUE;
    @JsonInclude(USE_DEFAULTS)
    private Boolean subscribed;
    @JsonInclude(USE_DEFAULTS)
    private Integer minQuantity = QUANTITY_MIN_DEFAULT_VALUE;

}
