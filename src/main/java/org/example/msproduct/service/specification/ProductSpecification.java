package org.example.msproduct.service.specification;

import lombok.AllArgsConstructor;
import org.example.msproduct.criteria.ProductCriteria;
import org.example.msproduct.entity.Product;
import org.example.msproduct.util.PredicateUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static org.example.msproduct.constant.CriteriaConstants.*;
import static org.example.msproduct.util.PredicateUtil.applyLikePattern;

@AllArgsConstructor
public class ProductSpecification implements Specification<Product> {
    private ProductCriteria productCriteria;

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate[] predicates = PredicateUtil.builder()
                .addNullSafety(productCriteria.getName(),
                        name -> cb.like(root.get(NAME), applyLikePattern(name)))
                .addNullSafety(productCriteria.getDescription(),
                        description -> cb.like(root.get(DESCRIPTION), applyLikePattern(description)))
                .addNullSafety(productCriteria.getCategoryId(),
                        categoryId-> cb.equal(root.get(CATEGORY_ID), categoryId))
                .addNullSafety(productCriteria.getSubscribed(),
                        subscribed-> cb.equal(root.get(SUBSCRIBED), subscribed))
                .add(productCriteria.getMinPrice(),
                        minPrice -> cb.greaterThanOrEqualTo(root.get(PRICE), minPrice))
                .add(productCriteria.getMaxPrice(),
                        maxPrice -> cb.lessThanOrEqualTo(root.get(PRICE), maxPrice))
                .add(productCriteria.getMinRating(),
                        minRating->cb.greaterThanOrEqualTo(root.get(RATING), minRating))
                .add(productCriteria.getMaxRating(),
                        maxRating -> cb.lessThanOrEqualTo(root.get(RATING), maxRating))
                .add(productCriteria.getMinQuantity(),
                        minQuantity -> cb.greaterThan(root.get(QUANTITY), minQuantity))
                .build();
        return cb.and(predicates);
    }
}
