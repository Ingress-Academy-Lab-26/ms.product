package org.example.msproduct.mapper;

import lombok.SneakyThrows;
import org.example.msproduct.dao.entity.ProductImage;
import org.example.msproduct.model.request.ProductImageRequest;
import org.example.msproduct.model.response.ProductImageResponse;

import java.util.Base64;

import static org.example.msproduct.model.enums.Status.ACTIVE;

public enum ImageMapper {
    IMAGE_MAPPER;

    @SneakyThrows
    public ProductImage mapToEntity(ProductImageRequest request) {
        return ProductImage.builder()
                .encodedImage(request.getImage())
                .status(ACTIVE)
                .isMain(request.getIsMain())
                .build();
    }

    @SneakyThrows
    public ProductImageResponse mapToResponse(ProductImage image) {
        return ProductImageResponse.builder()
                .id(image.getId())
                .isMain(image.getIsMain())
                .image(Base64.getDecoder().decode(image.getEncodedImage()))
                .build();
    }
}
