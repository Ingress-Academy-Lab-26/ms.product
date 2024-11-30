package org.example.msproduct.controller.internal;

import lombok.RequiredArgsConstructor;
import org.example.msproduct.model.dto.ProductQuantity;
import org.example.msproduct.service.abstraction.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("internal/v1/products")
@RequiredArgsConstructor
public class InternalProductController {

    private final ProductService productService;

    @PostMapping("/quantity")
    public List<ProductQuantity> getProductQuantityByIds(@RequestBody List<Long> productIds) {
        return productService.getProductQuantities(productIds);
    }

}
