package com.coffee.product.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    @NonNull
    private long product_id;    // 상품코드
    private String name;        // 상품명
    private long price;         // 가격
    private String image;       // 이미지

}
