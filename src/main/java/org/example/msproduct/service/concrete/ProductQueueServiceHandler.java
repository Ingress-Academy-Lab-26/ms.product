package org.example.msproduct.service.concrete;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.msproduct.annotation.Loggable;
import org.example.msproduct.dao.entity.Product;
import org.example.msproduct.dao.repository.ProductRepository;
import org.example.msproduct.exception.NotFoundException;
import org.example.msproduct.exception.ProductQuantityException;
import org.example.msproduct.model.dto.ProductQuantityUpdate;
import org.example.msproduct.model.queue.dto.RatingProductDto;
import org.example.msproduct.model.queue.dto.SubscriptionProductDto;
import org.example.msproduct.service.abstraction.ProductQueueService;
import org.example.msproduct.util.CacheUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.example.msproduct.model.constants.CacheConstants.CACHE_PRODUCT_DEFAULT_KEY;
import static org.example.msproduct.model.constants.ErrorConstants.PRODUCT_NOT_FOUND_EXCEPTION;
import static org.example.msproduct.model.constants.ErrorConstants.PRODUCT_QUANTITY_EXCEPTION;

@Service
@Slf4j
@Loggable
@RequiredArgsConstructor
public class ProductQueueServiceHandler implements ProductQueueService {
    private final ProductRepository productRepository;
    private final CacheUtil cacheUtil;

    @Override
    @Transactional
    @CircuitBreaker(name = "productService", fallbackMethod = "fallBackForGetProduct")
    public void productQuantityUpdate(List<ProductQuantityUpdate> productQuantity) {
        productQuantity.forEach(prd -> {
            if (prd.getProductId() != null) {

                var product = fetchProductQuantity(prd.getProductId());

                if (product.getQuantity() >= prd.getQuantity()) {

                    product.setQuantity(product.getQuantity() - prd.getQuantity());
                    productRepository.updateQuantityById(prd.getProductId(),
                            product.getQuantity() - prd.getQuantity());

                    Product cacheProduct = cacheUtil.getBucket(CACHE_PRODUCT_DEFAULT_KEY + prd.getProductId());
                    if (cacheProduct != null) {
                        cacheProduct.setQuantity(product.getQuantity() - prd.getQuantity());
                        cacheUtil.setBucket(CACHE_PRODUCT_DEFAULT_KEY + prd.getProductId(),
                                cacheProduct, 1L, DAYS);
                    }
                } else {
                    throw new ProductQuantityException(PRODUCT_QUANTITY_EXCEPTION.getCode(),
                            PRODUCT_QUANTITY_EXCEPTION.getMessage());
                }
            }
        });
    }

    @Override
    @Transactional
    public void ratingUpdate(RatingProductDto rating) {
        if (rating != null && rating.getProductId() != null && rating.getRatingAverage() != null) {
            productIsExist(rating.getProductId());
            log.info("ratingUpdate: {}", rating);
            productRepository.updateRatingById(rating.getProductId(), rating.getRatingAverage());
            var product = cacheUtil.getBucket(CACHE_PRODUCT_DEFAULT_KEY + rating.getProductId());
            if (product != null) {
                cacheUtil.setBucket(CACHE_PRODUCT_DEFAULT_KEY + rating.getProductId(),
                        product, 1L, DAYS);
            }
        }
    }

    @Override
    @Transactional
    public void subscriptionUpdate(SubscriptionProductDto subscription) {
        if (subscription != null && subscription.getProductId() != null) {
            productIsExist(subscription.getProductId());
            productRepository.updateSubscribedById(subscription.getProductId(), subscription.isSubscribed());
            var product = cacheUtil.getBucket(CACHE_PRODUCT_DEFAULT_KEY + subscription.getProductId());
            if (product != null) {
                cacheUtil.setBucket(CACHE_PRODUCT_DEFAULT_KEY + subscription.getProductId(),
                        product, 1L, DAYS);
            }
        }
    }

    private Product fetchProductQuantity(Long id) {
        return productRepository.getQuantityById(id)
                .orElseThrow(() -> new NotFoundException(PRODUCT_NOT_FOUND_EXCEPTION.getCode(),
                        PRODUCT_NOT_FOUND_EXCEPTION.getMessage()));
    }

    private void productIsExist(Long id) {
        if (!productRepository.existsById(id)) throw new NotFoundException(PRODUCT_NOT_FOUND_EXCEPTION.getCode(),
                PRODUCT_NOT_FOUND_EXCEPTION.getMessage());
    }

    public void fallBackForGetProduct(List<ProductQuantityUpdate> productQuantity, Throwable e) {
        log.error("ActionLog.ProductService.ProductQuantityUpdate: {}, Error: {}", productQuantity, e.getMessage(), e);
    }

}
