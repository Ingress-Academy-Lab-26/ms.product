package org.example.msproduct.model.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.msproduct.model.dto.Features;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

import static org.example.msproduct.model.constants.ValidationConstants.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCreateRequest {
    @NotEmpty(message = PRODUCT_NAME_EMPTY)
    private String name;
    @NotEmpty(message = PRODUCT_DESCRIPTION_EMPTY)
    private String description;
    @NotNull(message = PRODUCT_PRICE_NULL)
    private BigDecimal price;
    @NotNull(message = PRODUCT_QUANTITY_NULL)
    private Integer quantity;
    @NotNull(message = PRODUCT_CATEGORY_ID_NULL)
    private Long categoryId;
    private List<Features> features;
    @NotEmpty(message = PRODUCT_IMAGE_NULL_OR_EMPTY)
    private List<ProductImageRequest> images;
}
