package org.example.msproduct.service.concrete;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.msproduct.annotation.ElapsedTimeLogger;
import org.example.msproduct.annotation.Loggable;
import org.example.msproduct.criteria.PageCriteria;
import org.example.msproduct.criteria.ProductCriteria;
import org.example.msproduct.entity.Product;
import org.example.msproduct.entity.ProductImage;
import org.example.msproduct.exception.NotFoundException;
import org.example.msproduct.exception.ProductQuantityException;
import org.example.msproduct.model.dto.OrderProduct;
import org.example.msproduct.model.queue.dto.RatingProductDto;
import org.example.msproduct.model.queue.dto.SubscriptionProductDto;
import org.example.msproduct.model.request.OrderRequest;
import org.example.msproduct.model.request.ProductCreateRequest;
import org.example.msproduct.model.request.ProductUpdateRequest;
import org.example.msproduct.model.response.OrderResponse;
import org.example.msproduct.model.response.PageableResponse;
import org.example.msproduct.model.response.ProductResponse;
import org.example.msproduct.repository.ProductRepository;
import org.example.msproduct.service.abstraction.ProductService;
import org.example.msproduct.service.specification.ProductSpecification;
import org.example.msproduct.util.CacheUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.example.msproduct.constant.CacheConstants.CACHE_PRODUCT_DEFAULT_KEY;
import static org.example.msproduct.constant.CriteriaConstants.RATING;
import static org.example.msproduct.constant.CriteriaConstants.SUBSCRIBED;
import static org.example.msproduct.constant.ErrorConstants.PRODUCT_NOT_FOUND_EXCEPTION;
import static org.example.msproduct.constant.ErrorConstants.PRODUCT_QUANTITY_EXCEPTION;
import static org.example.msproduct.mapper.ImageMapper.IMAGE_MAPPER;
import static org.example.msproduct.mapper.ProductMapper.PRODUCT_MAPPER;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceHandler implements ProductService {
    private final ProductRepository productRepository;
    private final CacheUtil cacheUtil;


    @Override
    @Loggable
    public PageableResponse<ProductResponse> getProducts(PageCriteria pageCriteria, ProductCriteria productCriteria) {
        log.info("ActionLog.ProductService.getProducts.ProductCriteria={}", productCriteria);
        var productsPage = productRepository.findAll(
                new ProductSpecification(productCriteria),
                PageRequest.of(pageCriteria.getPage(), pageCriteria.getSize(), Sort.by(SUBSCRIBED,RATING).descending()));
        return PRODUCT_MAPPER.mapToPageableProductResponse(productsPage);
    }

    @Override
    @Loggable
    @ElapsedTimeLogger
    public ProductResponse getProduct(Long id) {
        log.info("ActionLog.ProductService.getProduct.Info.Id: {}", id);
        Product product;
        try {
            product = cacheUtil.getBucket(CACHE_PRODUCT_DEFAULT_KEY + id);
            if (product != null) {
                log.info("ActionLog.ProductService.getProduct.FromCache.Id: {}", id);
                log.info("ActionLog.ProductService.getProduct.FromCache.Product: {}", product);
                return PRODUCT_MAPPER.mapToResponse(product);
            }
        } catch (Exception e) {
            log.error("ActionLog.ProductService.getProduct.FromCache.Failure.Id: {}, Error: {}", id, e.getMessage(), e);
        }

        product = fetchProduct(id);
        log.info("ActionLog.ProductService.getProduct.FromDatabase.Id: {}", id);
        log.info("ActionLog.ProductService.getProduct.FromDatabase.Product: {}", product);
        cacheUtil.setBucket(CACHE_PRODUCT_DEFAULT_KEY + id, product, 1L, DAYS);
        log.info("ActionLog.ProductService.getProduct.WriteCache.Id: {}", id);
        return PRODUCT_MAPPER.mapToResponse(product);
    }

    @Override
    @Loggable
    @Transactional
    public void createProduct(ProductCreateRequest productCreateRequest) {
        var product = PRODUCT_MAPPER.mapToEntity(productCreateRequest);
        product.getImages().forEach(it -> it.setProduct(product));
        var response = productRepository.save(product);
        cacheUtil.setBucket(CACHE_PRODUCT_DEFAULT_KEY + response.getId(), product, 1L, DAYS);
        log.info("ActionLog.ProductService.createProduct.WriteCache.Id: {}", response.getId());
    }

    @Override
    @Loggable
    @Transactional
    public ProductResponse updateProduct(Long id, ProductUpdateRequest updateRequest) {
        log.info("ActionLog.ProductService.updateProduct.Id: {}", id);
        var product = fetchProduct(id);
        var response = productRepository.save(setUpdatedFields(product, updateRequest));
        cacheUtil.setBucket(CACHE_PRODUCT_DEFAULT_KEY + id, response, 1L, DAYS);
        log.info("ActionLog.ProductService.updateProduct.WriteCache.Id: {}", response.getId());
        return PRODUCT_MAPPER.mapToResponse(response);
    }

    @Override
    @Loggable
    @Transactional
    public void deleteProduct(Long id) {
        log.info("ActionLog.ProductService.deleteProduct.Id: {}", id);
        var product = fetchProduct(id);
        product.setIsDeleted(true);
        List<ProductImage> images = product.getImages();
        images.forEach(it -> it.setIsDeleted(true));
        product.setImages(images);
        cacheUtil.deleteFromCache(CACHE_PRODUCT_DEFAULT_KEY + id);
        log.info("ActionLog.ProductService.deleteProduct.DeleteCache.Id: {}", id);
        productRepository.save(product);
    }

    @Override
    @Loggable
    public OrderResponse getQuantity(OrderRequest orderRequest) {
        List<OrderProduct> products = new ArrayList<>();
        orderRequest.getProducts().forEach(prd -> {
            log.info("ActionLog.ProductService.getQuantity.Id: {}", prd.getProductId());
            var product = fetchProduct(prd.getProductId());
            products.add(PRODUCT_MAPPER.mapToOrderProduct(product));
        });
        return OrderResponse.builder()
                .orderProducts(products)
                .build();
    }


    @Override
    @Loggable
    public void productQuantityUpdate(OrderRequest orderRequest) {
        log.info("ActionLog.ProductService.productQuantityUpdate.OrderRequest: {}", orderRequest);
        List<Product> products = new ArrayList<>();
        orderRequest.getProducts().forEach(prd -> {
            if (prd.getProductId() != null) {
                var product = fetchProduct(prd.getProductId());
                if (product.getQuantity() >= prd.getQuantity()) {
                    product.setQuantity(product.getQuantity() - prd.getQuantity());
                    products.add(product);
                } else {
                    throw new ProductQuantityException(PRODUCT_QUANTITY_EXCEPTION.getCode(),
                            PRODUCT_QUANTITY_EXCEPTION.getMessage());
                }
            }
        });
        products.forEach(prd -> {
            cacheUtil.setBucket(CACHE_PRODUCT_DEFAULT_KEY + prd.getId(), prd, 1L, DAYS);
            log.info("ActionLog.ProductService.productQuantityUpdate.WriteCache.Id: {}", prd.getId());
        });
        if (!products.isEmpty())
            productRepository.saveAll(products);
    }

    @Override
    @Loggable
    public void ratingUpdate(RatingProductDto rating) {
        if (rating != null && rating.getProductId() != null && rating.getRatingAverage() != null) {
            log.info("ActionLog.ProductService.ratingUpdate.Id: {} RatingAverage: {}", rating.getProductId(),
                    rating.getRatingAverage());
            var product = fetchProduct(rating.getProductId());
            product.setRating(rating.getRatingAverage());
            cacheUtil.setBucket(CACHE_PRODUCT_DEFAULT_KEY + product.getId(), product, 1L, DAYS);
            log.info("ActionLog.ProductService.RatingUpdate.WriteCache.Id: {}", product.getId());
            productRepository.save(product);
        }
    }

    @Override
    @Loggable
    public void subscriptionUpdate(SubscriptionProductDto subscription) {
        if (subscription != null && subscription.getProductId() != null) {
            var product = fetchProduct(subscription.getProductId());
            product.setSubscribed(subscription.isSubscribed());
            cacheUtil.setBucket(CACHE_PRODUCT_DEFAULT_KEY + product.getId(), product, 1L, DAYS);
            log.info("ActionLog.ProductService.SubscriptionUpdate.WriteCache.Id: {}", product.getId());
            productRepository.save(product);
        }
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
