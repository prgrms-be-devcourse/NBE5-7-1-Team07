package com.coffee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class OrderProductRequest { //서브 DTO(주문 생성요청시 하나하나의 주문 정보를 담기위함)
    private Long productId;
    private int quantity;
}
