package com.coffee.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private int quantity;

    public void setOrder(Order order){
        this.order = order;             //양방향
    }

    public OrderProduct(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}
