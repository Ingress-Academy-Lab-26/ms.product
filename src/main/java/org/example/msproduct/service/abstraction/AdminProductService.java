package org.example.msproduct.service.abstraction;

import org.example.msproduct.model.request.ProductCreateRequest;
import org.example.msproduct.model.request.ProductUpdateRequest;
import org.example.msproduct.model.response.ProductResponse;

public interface AdminProductService {
    void createProduct(ProductCreateRequest productCreateRequest);

    ProductResponse updateProduct(Long id, ProductUpdateRequest updateRequest);

    void deleteProduct(Long id);

}
