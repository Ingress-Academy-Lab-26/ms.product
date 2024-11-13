package org.example.msproduct.model.response;

import lombok.Builder;
import lombok.Data;
import org.example.msproduct.model.dto.OrderProduct;

import java.util.List;

@Data
@Builder
public class OrderResponse {
    private List<OrderProduct> orderProducts;
}
