package org.example.msproduct.mapper;

import org.example.msproduct.entity.Product;
import org.example.msproduct.model.dto.OrderProduct;
import org.example.msproduct.model.request.ProductCreateRequest;
import org.example.msproduct.model.response.PageableResponse;
import org.example.msproduct.model.response.ProductResponse;
import org.springframework.data.domain.Page;

import static org.example.msproduct.mapper.ImageMapper.IMAGE_MAPPER;


public enum ProductMapper {
    PRODUCT_MAPPER;

    public Product mapToEntity(ProductCreateRequest request) {
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .categoryId(request.getCategoryId())
                .subscribed(false)
                .rating(0.0)
                .isDeleted(false)
                .features(request.getFeatures())
                .images(request.getImages().stream().map(IMAGE_MAPPER::mapToEntity).toList())
                .build();
    }

    public ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .rating(product.getRating())
                .subscribed(product.getSubscribed())
                .features(product.getFeatures())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .images(product.getImages().stream().map(IMAGE_MAPPER::mapToResponse).toList())
                .build();
    }

    public PageableResponse<ProductResponse> mapToPageableProductResponse(Page<Product> pages) {
        return PageableResponse.<ProductResponse>builder()
                .products(pages.getContent().stream().map(this::mapToResponse).toList())
                .lastPageNumber(pages.getTotalPages())
                .totalElementsCount(pages.getTotalElements())
                .hasNextPage(pages.hasNext())
                .build();
    }

    public OrderProduct mapToOrderProduct(Product product) {
        return OrderProduct.builder()
                .productId(product.getId())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .build();
    }



}
