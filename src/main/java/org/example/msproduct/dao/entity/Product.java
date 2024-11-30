package org.example.msproduct.dao.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.example.msproduct.model.dto.Features;
import org.example.msproduct.model.enums.Status;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "products")
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "status = 'ACTIVE'")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@NamedEntityGraph(name = "images-entity-graph", attributeNodes =@NamedAttributeNode("images"))
public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Long categoryId;
    private Integer quantity;
    private BigDecimal rating;
    private Boolean subscribed;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Features> features;
    @OneToMany(mappedBy = "product",fetch = LAZY, cascade = {PERSIST, MERGE})
    private List<ProductImage> images;
    @Enumerated(STRING)
    private Status status;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
