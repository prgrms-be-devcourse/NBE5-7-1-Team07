package com.coffee.dto;

import com.coffee.domain.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    private String email;
    private String address;
    private String postcode;
    private List<OrderProductRequest> products;


}
