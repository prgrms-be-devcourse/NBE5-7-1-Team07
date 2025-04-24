package com.coffee.dto;

import com.coffee.domain.DeliveryStatus;
import com.coffee.domain.Order;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderResponse {//주문생성 및 조회후 클라이언트에게 전달할 정보를 담는 용도
    private final Long orderId;
    private final String email;
    private final String address;
    private final String postcode;
    private final DeliveryStatus deliveryStatus;
    private final LocalDateTime createdAt;
    private final List<OrderProductResponse> products;
    private final int totalPrice;

    public OrderResponse(Order order){
        this.orderId = order.getId();
        this.email = order.getEmail();
        this.address = order.getAddress();
        this.postcode = order.getPostcode();
        this.deliveryStatus = order.getDeliveryStatus();
        this.createdAt = order.getCreatedAt();
        this.products = order.getOrderProducts().stream()
                .map(OrderProductResponse::new)
                .collect(Collectors.toList());
        this.totalPrice = order.getTotalPrice();

    }
}
