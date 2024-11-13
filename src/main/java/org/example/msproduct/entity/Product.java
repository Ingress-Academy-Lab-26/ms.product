package org.example.msproduct.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "products")
@Where(clause = "is_deleted = false")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(name = "images-features-entity-graph", attributeNodes = {@NamedAttributeNode("features"), @NamedAttributeNode("images")})
public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    @Column(name = "category_id")
    private Long categoryId;
    private Integer quantity;
    private Double rating;
    private Boolean subscribed;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "features",
            joinColumns = @JoinColumn(name = "product_id"))
    @MapKeyColumn(name = "feature_key")
    @Column(name = "feature_value")
    private Map<String, String> features;
    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY, cascade = {PERSIST, MERGE, REMOVE})
    private List<ProductImage> images;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Product product = (Product) object;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
