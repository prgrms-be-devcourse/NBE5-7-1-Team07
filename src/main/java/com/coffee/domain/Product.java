package com.coffee.domain;

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
    @NonNull
    @Column(name="product_id")
    private long product_id;    // 상품코드

    @NonNull
    @Column(name="name")
    private String name;        // 상품명

    @NonNull
    @Column(name="price")
    private long price;         // 가격

    @Column(name="image")
    private String image;       // 이미지

}
