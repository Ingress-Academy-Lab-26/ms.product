package org.example.msproduct.service.abstraction;

import org.example.msproduct.criteria.PageCriteria;
import org.example.msproduct.criteria.ProductCriteria;
import org.example.msproduct.model.queue.dto.RatingProductDto;
import org.example.msproduct.model.queue.dto.SubscriptionProductDto;
import org.example.msproduct.model.request.OrderRequest;
import org.example.msproduct.model.request.ProductCreateRequest;
import org.example.msproduct.model.request.ProductUpdateRequest;
import org.example.msproduct.model.response.OrderResponse;
import org.example.msproduct.model.response.PageableResponse;
import org.example.msproduct.model.response.ProductResponse;

public interface ProductService {
    PageableResponse<ProductResponse> getProducts(PageCriteria pageCriteria, ProductCriteria productCriteria);

    ProductResponse getProduct(Long id);

    void createProduct(ProductCreateRequest productCreateRequest);

    ProductResponse updateProduct(Long id, ProductUpdateRequest updateRequest);

    void deleteProduct(Long id);

    void ratingUpdate(RatingProductDto rating);

    void productQuantityUpdate(OrderRequest orderRequest);

    void subscriptionUpdate(SubscriptionProductDto subscription);

    OrderResponse getQuantity(OrderRequest orderRequest);
}
