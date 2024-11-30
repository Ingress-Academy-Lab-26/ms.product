package org.example.msproduct.service.concrete;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.msproduct.annotation.Loggable;
import org.example.msproduct.dao.entity.Product;
import org.example.msproduct.dao.repository.ProductRepository;
import org.example.msproduct.exception.NotFoundException;
import org.example.msproduct.model.criteria.PageCriteria;
import org.example.msproduct.model.criteria.ProductCriteria;
import org.example.msproduct.model.dto.ProductQuantity;
import org.example.msproduct.model.response.PageableResponse;
import org.example.msproduct.model.response.ProductResponse;
import org.example.msproduct.service.abstraction.ProductService;
import org.example.msproduct.service.specification.ProductSpecification;
import org.example.msproduct.util.CacheUtil;
import org.example.msproduct.util.SortUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.example.msproduct.mapper.ProductMapper.PRODUCT_MAPPER;
import static org.example.msproduct.model.constants.CacheConstants.CACHE_PRODUCT_DEFAULT_KEY;
import static org.example.msproduct.model.constants.ErrorConstants.PRODUCT_NOT_FOUND_EXCEPTION;

@Slf4j
@Service
@Loggable
@RequiredArgsConstructor
public class ProductServiceHandler implements ProductService {
    private final ProductRepository productRepository;
    private final CacheUtil cacheUtil;
    private final SortUtil sortUtil;

    @Override
    public PageableResponse<ProductResponse> getProducts(PageCriteria pageCriteria, ProductCriteria productCriteria) {
        var sort = sortUtil.getSort(productCriteria);
        var productsPage = productRepository.findAll(
                ProductSpecification.of(productCriteria),
                PageRequest.of(
                        pageCriteria.getPage(),
                        pageCriteria.getSize(),
                        sort)
        );
        return PRODUCT_MAPPER.mapToPageableProductResponse(productsPage);
    }

    @Override
    @CircuitBreaker(name = "productService", fallbackMethod = "fallBackForGetProduct")
    public ProductResponse getProduct(Long id) {
        Product product;
        product = cacheUtil.getBucket(CACHE_PRODUCT_DEFAULT_KEY + id);
        if (product != null) {
            return PRODUCT_MAPPER.mapToResponse(product);
        }
        product = fetchProduct(id);
        cacheUtil.setBucket(CACHE_PRODUCT_DEFAULT_KEY + id, product, 1L, DAYS);
        return PRODUCT_MAPPER.mapToResponse(product);
    }

    @Override
    public List<ProductQuantity> getProductQuantities(List<Long> productIds) {
        List<ProductQuantity> products = new ArrayList<>();
        productIds.forEach(prd -> {
            var product = fetchProduct(prd);
            products.add(PRODUCT_MAPPER.mapToOrderProduct(product));
        });
        return products;
    }

    private Product fetchProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(PRODUCT_NOT_FOUND_EXCEPTION.getCode(),
                        PRODUCT_NOT_FOUND_EXCEPTION.getMessage()));
    }

    public ProductResponse fallBackForGetProduct(Long id, Throwable e) {
        log.error("ActionLog.ProductService.getProduct.FromCache.Failure.Id: {}, Error: {}", id, e.getMessage(), e);
        return ProductResponse.builder().build();
    }


}
