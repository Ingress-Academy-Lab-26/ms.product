package org.example.msproduct.controller.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.msproduct.model.request.ProductCreateRequest;
import org.example.msproduct.model.request.ProductUpdateRequest;
import org.example.msproduct.model.response.ProductResponse;
import org.example.msproduct.service.abstraction.AdminProductService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@Slf4j
@RequestMapping("internal/v1/admin/products")
@Validated
@RequiredArgsConstructor
public class AdminProductController {
    private final AdminProductService adminProductService;

    @PostMapping()
    @ResponseStatus(CREATED)
    public void createProduct(@RequestBody @Valid ProductCreateRequest productCreateRequest) {
        log.info("Create product: {}", productCreateRequest);
        adminProductService.createProduct(productCreateRequest);
    }

    @PatchMapping(value = "/{id}", consumes = "multipart/form-data")
    @ResponseStatus(NO_CONTENT)
    public ProductResponse updateProduct(@ModelAttribute ProductUpdateRequest updateRequest, @PathVariable long id) {
        return adminProductService.updateProduct(id, updateRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteProduct(@PathVariable long id) {
        adminProductService.deleteProduct(id);
    }
}
