package org.example.msproduct.model.request;

import lombok.Builder;
import lombok.Data;
import org.example.msproduct.model.dto.OrderProductQuantity;

import java.util.List;

@Data
@Builder
public class OrderRequest {
    private List<OrderProductQuantity> products;
}
