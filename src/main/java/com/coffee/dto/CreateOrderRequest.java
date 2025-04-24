package com.coffee.dto;

import com.coffee.domain.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CreateOrderRequest {
    private String email;
    private String address;
    private String postcode;
    private List<OrderProductRequest> products = new ArrayList<>(); // ✅ 초기화 추가

    public CreateOrderRequest() {}

    public CreateOrderRequest(String email, String address, String postcode, List<OrderProductRequest> products) {
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.products = products;
    }
}


