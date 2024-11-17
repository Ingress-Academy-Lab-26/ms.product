package org.example.msproduct.controller.internal;

import lombok.RequiredArgsConstructor;
import org.example.msproduct.model.request.OrderRequest;
import org.example.msproduct.model.request.ProductCreateRequest;
import org.example.msproduct.model.request.ProductUpdateRequest;
import org.example.msproduct.model.response.OrderResponse;
import org.example.msproduct.model.response.ProductResponse;
import org.example.msproduct.service.abstraction.ProductService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("v1/internal/products")
@RequiredArgsConstructor
public class InternalProductController {

    private final ProductService productService;

    @GetMapping("/getQuantity")
    @ResponseStatus(OK)
    public OrderResponse getQuantityById(@RequestBody OrderRequest orderRequest) {
        return productService.getQuantity(orderRequest);
    }

    @PostMapping(consumes = {"multipart/form-data", "application/json"})
    @ResponseStatus(CREATED)
    public void createProduct(@ModelAttribute @Validated ProductCreateRequest productCreateRequest) {
        productService.createProduct(productCreateRequest);
    }

    @PatchMapping(value = "/{id}", consumes = "multipart/form-data")
    @ResponseStatus(NO_CONTENT)
    public ProductResponse updateProduct(@ModelAttribute ProductUpdateRequest updateRequest, @PathVariable long id) {
        return productService.updateProduct(id, updateRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
    }
}
