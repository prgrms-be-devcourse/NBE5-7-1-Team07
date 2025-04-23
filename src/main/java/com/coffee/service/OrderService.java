package com.coffee.service;

import com.coffee.domain.DeliveryStatus;
import com.coffee.domain.Order;
import com.coffee.domain.OrderProduct;
import com.coffee.domain.Product;
import com.coffee.dto.CreateOrderRequest;
import com.coffee.dto.OrderProductRequest;
import com.coffee.dto.OrderResponse;
import com.coffee.repository.OrderRepository;
import com.coffee.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request){  //리팩토링 필요함

        if (request.getProducts() == null || request.getProducts().isEmpty()) {
            throw new IllegalArgumentException("하나 이상의 상품을 선택해주세요.");
        }

        Order order = Order.builder()
                .email(request.getEmail())
                .address(request.getAddress())
                .postcode(request.getPostcode())
                .deliveryStatus(DeliveryStatus.READY)
                .createdAt(LocalDateTime.now())
                .build();


        for(OrderProductRequest productRequest : request.getProducts()){
            Product product = productRepository.findById(productRequest.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다.!"));

            OrderProduct orderProduct = OrderProduct.builder()
                    .order(order)
                    .product(product)
                    .quantity(productRequest.getQuantity())
                    .build();

            order.addOrderProduct(orderProduct);
        }

        Order savedOrder = orderRepository.save(order);
        savedOrder.calculateTotalPrice();

        return new OrderResponse(savedOrder);
    }


    //이메일로 주문찾기
    public List<OrderResponse> findOrdersByEmail(String email){
        List<Order> orders = orderRepository.findByEmail(email);
        return orders.stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }

}
