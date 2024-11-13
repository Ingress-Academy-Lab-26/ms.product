package org.example.msproduct.controller.internal;

import lombok.RequiredArgsConstructor;
import org.example.msproduct.model.request.OrderRequest;
import org.example.msproduct.model.response.OrderResponse;
import org.example.msproduct.service.abstraction.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/internal/product")
@RequiredArgsConstructor
public class InternalProductController {
    private final ProductService productService;

    @PostMapping("/get-quantity")
    public ResponseEntity<OrderResponse> getQuantityById(@RequestBody OrderRequest orderRequest) {
        var orderResponse = productService.getQuantity(orderRequest);
        return ResponseEntity.ok(orderResponse);
    }

    @PostMapping("/update-quantity")
    public ResponseEntity<Void> updateQuantity(@RequestBody OrderRequest orderRequest) {
        productService.productQuantityUpdate(orderRequest);
        return ResponseEntity.noContent().build();
    }

}
