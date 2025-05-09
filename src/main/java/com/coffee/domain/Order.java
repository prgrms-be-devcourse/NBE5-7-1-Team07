package com.coffee.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
    @Setter
    private String address;
    @Setter
    private String postcode;
    @Setter
    private LocalDateTime createdAt;


    @Enumerated(EnumType.STRING)
    @Getter
    private DeliveryStatus deliveryStatus;

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    private int totalPrice;

    public void addOrderProduct(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
    }

    public void calculateTotalPrice(){
        this.totalPrice = orderProducts.stream()
                .mapToInt(op -> op.getProduct().getPrice() * op.getQuantity())
                .sum();
    }

    public void startShipping(LocalDate today){
        this.deliveryStatus = DeliveryStatus.SHIPPING;
    }

    public void markCompleted() {
        this.deliveryStatus = DeliveryStatus.COMPLETED;
    }
}
