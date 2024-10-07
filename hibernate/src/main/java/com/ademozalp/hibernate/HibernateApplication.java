package com.ademozalp.hibernate;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
@EnableJpaAuditing
public class HibernateApplication implements CommandLineRunner {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private final EntityManager entityManager;

    public static void main(String[] args) {
        SpringApplication.run(HibernateApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) {
        Category category = Category.builder()
                .detail(Detail.builder().name("Aile Yemek Salonu").description("Yemek Salonu").build())
                .image("https://www.youtube.com/watch?v=-ecG6B_jNW8")
                .build();

        Product product = Product.builder()
                .detail(Detail.builder().name("Osmanlı Locası").description("Bol bol ye").build())
                .price(BigDecimal.valueOf(345.9))
                .category(category)
                .build();


        entityManager.persist(product);

        log.error("category: {}", product.getCategory().getId());
        log.error("category: {}", category.getId());
//
//        Category ct = entityManager.find(Category.class, "5a3527bf-ba8a-491f-aedc-a6f3df4964cf");
//
//        log.error("category: {}", ct);
//
//        ct.getProducts().get(0).setPrice(BigDecimal.valueOf(10));
//        ct.getDetail().setDescription("new description");
//
//        entityManager.refresh(ct);
//
//        log.error("price : {}", ct.getDetail().getDescription());
//
//        entityManager.remove(ct);
    }
}


@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
class Category extends BaseEntity {
    private String image;

    @Embedded
    private Detail detail;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
    private List<Product> products;
}

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Product extends BaseEntity {
    private BigDecimal price;

    @Embedded
    private Detail detail;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "categoryId")
    private Category category;
}


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Embeddable
@ToString
@Getter
@Setter
class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 100)
    private String id;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime created;

    @LastModifiedDate
    private LocalDateTime modified;
}

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Detail {
    private String name;
    private String description;
}


interface CategoryRepository extends JpaRepository<Category, String> {
}

interface ProductRepository extends JpaRepository<Product, String> {
}




