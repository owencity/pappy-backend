package com.kyu.pappy.entities;

import com.kyu.pappy.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private String productContent;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private int price;
    private int quantity;
    // 상품 품절관리 (판매중 , 품절 , 판매중지)

    @Enumerated(EnumType.STRING)
    private ProductStatus status;
    private Date createdAt;

    public void changeContent(String content) {
        this.productContent = content;
    }

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> ReviewList = new ArrayList<>();
}
