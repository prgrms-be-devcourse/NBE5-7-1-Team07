package com.coffee.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String address;
    private String postcode;
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    private int totalPrice;

    public void addOrderProduct(OrderProduct orderProduct) { //양방향
        this.orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
    }

    public void calculateTotalPrice(){
        this.totalPrice = orderProducts.stream()
                .mapToInt(op -> op.getProduct().getPrice() * op.getQuantity())
                .sum();
    }
}
