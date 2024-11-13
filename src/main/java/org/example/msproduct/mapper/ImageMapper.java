package org.example.msproduct.mapper;

import lombok.SneakyThrows;
import org.example.msproduct.entity.ProductImage;
import org.example.msproduct.model.request.ProductImageRequest;
import org.example.msproduct.model.response.ProductImageResponse;

import java.util.Base64;

public enum ImageMapper {
    IMAGE_MAPPER;

    @SneakyThrows
    public ProductImage mapToEntity(ProductImageRequest request) {
        String encodedFile = Base64.getEncoder().encodeToString(request.getImage().getBytes());
        return ProductImage.builder()
                .name(request.getImage().getOriginalFilename())
                .type(request.getImage().getContentType())
                .encodedImage(encodedFile)
                .isDeleted(false)
                .isMain(request.getIsMain())
                .build();
    }

    @SneakyThrows
    public ProductImageResponse mapToResponse(ProductImage image) {
        return ProductImageResponse.builder()
                .id(image.getId())
                .name(image.getName())
                .type(image.getType())
                .isMain(image.getIsMain())
                .image(Base64.getDecoder().decode(image.getEncodedImage()))
                .build();
    }
}
