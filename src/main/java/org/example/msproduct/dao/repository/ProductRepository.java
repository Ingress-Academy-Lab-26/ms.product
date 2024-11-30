package org.example.msproduct.dao.repository;

import org.example.msproduct.dao.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    @Override
    @EntityGraph(value = "images-entity-graph")
    Page<Product> findAll(Specification<Product> spec, Pageable pageable);

    @Override
    @EntityGraph(value = "images-entity-graph")
    Optional<Product> findById(Long aLong);

    @Query("SELECT p.id, p.quantity, p.price FROM Product p WHERE p.id = :id")
    Optional<Product> getQuantityAndPriceById(Long id);

    @Query("SELECT p.id, p.quantity FROM Product p WHERE p.id = :id")
    Optional<Product> getQuantityById(Long id);

    @Modifying
    @Query("UPDATE Product p SET p.subscribed = :isSubscribed WHERE p.id = :id")
    int updateSubscribedById(long id, boolean isSubscribed);

    @Modifying
    @Query("UPDATE Product p SET p.rating = :rating WHERE p.id = :id")
    int updateRatingById(long id, BigDecimal rating);

    @Modifying
    @Query("UPDATE Product p SET p.quantity = :quantity WHERE p.id = :id")
    int updateQuantityById(long id, int quantity);

}
