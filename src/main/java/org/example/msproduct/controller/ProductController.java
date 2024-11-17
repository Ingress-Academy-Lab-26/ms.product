package org.example.msproduct.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.msproduct.criteria.PageCriteria;
import org.example.msproduct.criteria.ProductCriteria;
import org.example.msproduct.model.response.PageableResponse;
import org.example.msproduct.model.response.ProductResponse;
import org.example.msproduct.service.abstraction.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    @GetMapping
    @ResponseStatus(OK)
    @PreAuthorize("@authServiceHandler.hasAuthority(#authorizationHeader)")
    public PageableResponse<ProductResponse> getAllProducts(
            @RequestHeader("Authorization") String authorizationHeader,
            PageCriteria pageCriteria,
            ProductCriteria productCriteria) {
        return productService.getProducts(pageCriteria, productCriteria);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    @PreAuthorize("@authServiceHandler.hasAuthority(#authorizationHeader)")
    public ProductResponse getProductById(@RequestHeader("Authorization") String authorizationHeader,
                                          @PathVariable long id) {
        return productService.getProduct(id);
    }

}
