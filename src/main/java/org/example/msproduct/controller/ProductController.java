package org.example.msproduct.controller;

import lombok.RequiredArgsConstructor;
import org.example.msproduct.model.criteria.PageCriteria;
import org.example.msproduct.model.criteria.ProductCriteria;
import org.example.msproduct.model.response.PageableResponse;
import org.example.msproduct.model.response.ProductResponse;
import org.example.msproduct.service.abstraction.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@RestController
@RequestMapping("v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    //@PreAuthorize("@authServiceHandler.hasAuthority(#authorizationHeader)")
    public PageableResponse<ProductResponse> getAllProducts(
           // @RequestHeader(AUTHORIZATION) String authorizationHeader,
            PageCriteria pageCriteria,
            ProductCriteria productCriteria) {
        return productService.getProducts(pageCriteria, productCriteria);
    }

    @GetMapping("/{id}")
   // @PreAuthorize("@authServiceHandler.hasAuthority(#authorizationHeader)")
    public ProductResponse getProductById(
//            @RequestHeader(AUTHORIZATION) String authorizationHeader,
                                          @PathVariable long id) {
        return productService.getProduct(id);
    }

}
