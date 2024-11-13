package org.example.msproduct.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.msproduct.annotation.Loggable;
import org.example.msproduct.criteria.PageCriteria;
import org.example.msproduct.criteria.ProductCriteria;
import org.example.msproduct.model.request.OrderRequest;
import org.example.msproduct.model.request.ProductCreateRequest;
import org.example.msproduct.model.request.ProductUpdateRequest;
import org.example.msproduct.model.response.OrderResponse;
import org.example.msproduct.model.response.PageableResponse;
import org.example.msproduct.model.response.ProductResponse;
import org.example.msproduct.service.abstraction.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    @GetMapping
    @PreAuthorize("@authServiceHandler.hasAuthority(#authorizationHeader)")
    public ResponseEntity<PageableResponse<ProductResponse>> getAllProducts(
            @RequestHeader("Authorization") String authorizationHeader,
            PageCriteria pageCriteria,
            ProductCriteria productCriteria) {
        log.info("ActionLog.ProductController.GetAllProducts.AuthorizationHeader {}", authorizationHeader);
        return ResponseEntity.ok(productService.getProducts(pageCriteria, productCriteria));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@authServiceHandler.hasAuthority(#authorizationHeader)")
    public ResponseEntity<ProductResponse> getProductById(@RequestHeader("Authorization") String authorizationHeader,
                                                          @PathVariable long id) {
        log.info("ActionLog.ProductController.GetProductById.AuthorizationHeader {}", authorizationHeader);
        var product = productService.getProduct(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping(consumes = {"multipart/form-data", "application/json"})
    @PreAuthorize("@authServiceHandler.hasAuthority(#authorizationHeader)")
    public ResponseEntity<Void> createProduct(@RequestHeader("Authorization") String authorizationHeader,
                                              @ModelAttribute @Validated ProductCreateRequest productCreateRequest) {
        log.info("ActionLog.ProductController.CreateProduct.AuthorizationHeader {}", authorizationHeader);
        log.info("Creating product {}", productCreateRequest);
        productService.createProduct(productCreateRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{id}", consumes = "multipart/form-data")
    @PreAuthorize("@authServiceHandler.hasAuthority(#authorizationHeader)")
    public ResponseEntity<ProductResponse> updateProduct(@RequestHeader("Authorization") String authorizationHeader,
                                                         @ModelAttribute ProductUpdateRequest updateRequest, @PathVariable long id) {
        log.info("ActionLog.ProductController.UpdateProduct.AuthorizationHeader {}", authorizationHeader);
        log.info("Update product {}", updateRequest);
        var product = productService.updateProduct(id, updateRequest);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@authServiceHandler.hasAuthority(#authorizationHeader)")
    public ResponseEntity<Void> deleteProduct(@RequestHeader("Authorization") String authorizationHeader,
                                              @PathVariable long id) {
        log.info("ActionLog.ProductController.DeleteProduct.AuthorizationHeader {}", authorizationHeader);
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
