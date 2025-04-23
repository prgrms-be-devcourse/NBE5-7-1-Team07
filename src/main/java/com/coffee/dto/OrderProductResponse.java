package com.coffee.dto;

import com.coffee.domain.OrderProduct;
import lombok.Getter;

@Getter
public class OrderProductResponse {
    private final String productName;
    private final int quantity;

    public OrderProductResponse(OrderProduct orderProduct) {
        this.productName = orderProduct.getProduct().getProductName();
        this.quantity = orderProduct.getQuantity();
    }
}
