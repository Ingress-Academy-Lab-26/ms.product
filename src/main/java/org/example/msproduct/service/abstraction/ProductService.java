package org.example.msproduct.service.abstraction;

import org.example.msproduct.model.criteria.PageCriteria;
import org.example.msproduct.model.criteria.ProductCriteria;
import org.example.msproduct.model.dto.ProductQuantity;
import org.example.msproduct.model.response.PageableResponse;
import org.example.msproduct.model.response.ProductResponse;

import java.util.List;

public interface ProductService {
    PageableResponse<ProductResponse> getProducts(PageCriteria pageCriteria, ProductCriteria productCriteria);

    ProductResponse getProduct(Long id);

    List<ProductQuantity> getProductQuantities(List<Long> productIds);
}
