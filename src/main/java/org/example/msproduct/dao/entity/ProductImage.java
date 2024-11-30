package org.example.msproduct.dao.entity;

import lombok.*;
import org.example.msproduct.model.enums.Status;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "images")
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
@Where(clause = "status = 'ACTIVE'")
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String encodedImage;
    private Boolean isMain;
    @ManyToOne(fetch = LAZY, cascade = {PERSIST, MERGE})
    private Product product;
    @Enumerated(STRING)
    private Status status;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
