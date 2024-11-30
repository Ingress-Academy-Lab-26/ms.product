package org.example.msproduct.service.concrete;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.msproduct.annotation.Loggable;
import org.example.msproduct.dao.entity.Product;
import org.example.msproduct.dao.entity.ProductImage;
import org.example.msproduct.dao.repository.ProductRepository;
import org.example.msproduct.exception.NotFoundException;
import org.example.msproduct.model.request.ProductCreateRequest;
import org.example.msproduct.model.request.ProductUpdateRequest;
import org.example.msproduct.model.response.ProductResponse;
import org.example.msproduct.service.abstraction.AdminProductService;
import org.example.msproduct.util.CacheUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.example.msproduct.mapper.ImageMapper.IMAGE_MAPPER;
import static org.example.msproduct.mapper.ProductMapper.PRODUCT_MAPPER;
import static org.example.msproduct.model.constants.CacheConstants.CACHE_PRODUCT_DEFAULT_KEY;
import static org.example.msproduct.model.constants.ErrorConstants.PRODUCT_NOT_FOUND_EXCEPTION;
import static org.example.msproduct.model.enums.Status.DELETED;

@Service
@Slf4j
@Loggable
@RequiredArgsConstructor
public class AdminProductServiceHandler implements AdminProductService {

    private final ProductRepository productRepository;
    private final CacheUtil cacheUtil;


    @Override
    public void createProduct(ProductCreateRequest productCreateRequest) {
        var product = PRODUCT_MAPPER.mapToEntity(productCreateRequest);
        product.getImages().forEach(it -> it.setProduct(product));
        var response = productRepository.save(product);
        cacheUtil.setBucket(CACHE_PRODUCT_DEFAULT_KEY + response.getId(), product, 1L, DAYS);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductUpdateRequest updateRequest) {
        log.info("ActionLog.ProductService.updateProduct.Id: {}", id);
        var product = fetchProduct(id);
        var response = productRepository.save(setUpdatedFields(product, updateRequest));
        cacheUtil.setBucket(CACHE_PRODUCT_DEFAULT_KEY + id, response, 1L, DAYS);
        log.info("ActionLog.ProductService.updateProduct.WriteCache.Id: {}", response.getId());
        return PRODUCT_MAPPER.mapToResponse(response);
    }

    @Override
    public void deleteProduct(Long id) {
        var product = fetchProduct(id);
        product.setStatus(DELETED);
        List<ProductImage> images = product.getImages();
        images.forEach(it -> it.setStatus(DELETED));
        product.setImages(images);
        cacheUtil.deleteFromCache(CACHE_PRODUCT_DEFAULT_KEY + id);
        productRepository.save(product);
    }

    private Product fetchProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(PRODUCT_NOT_FOUND_EXCEPTION.getCode(),
                        PRODUCT_NOT_FOUND_EXCEPTION.getMessage()));
    }
    private Product setUpdatedFields(Product product, ProductUpdateRequest updateRequest) {
        Optional.ofNullable(updateRequest.getName()).ifPresent(product::setName);
        Optional.ofNullable(updateRequest.getDescription()).ifPresent(product::setDescription);
        Optional.ofNullable(updateRequest.getPrice()).ifPresent(product::setPrice);
        Optional.ofNullable(updateRequest.getQuantity()).ifPresent(product::setQuantity);
        Optional.ofNullable(updateRequest.getCategoryId()).ifPresent(product::setCategoryId);

        if (updateRequest.getFeatures() != null) {
            product.setFeatures(updateRequest.getFeatures());
        }

        if (updateRequest.getImages() != null) {
            var images = updateRequest.getImages().stream().map(IMAGE_MAPPER::mapToEntity).toList();
            images.forEach(it -> it.setProduct(product));
        }
        return product;
    }

}
