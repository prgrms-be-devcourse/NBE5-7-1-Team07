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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request){  //리팩토링 필요함

        if (request.getProducts() == null || request.getProducts().isEmpty()) {
            throw new IllegalArgumentException("하나 이상의 상품을 선택해주세요.");
        }

        Order order = convertToOrderEntity(request);

        for(OrderProductRequest orderProductRequest : request.getProducts()){
            Product product = productRepository.findById(orderProductRequest.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다.!"));

            OrderProduct orderProduct = convertToOrderProductEntity(orderProductRequest,order,product);

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



    //CreateOrderRequest dto -> entity
    private Order convertToOrderEntity(CreateOrderRequest request){
        Order order = Order.builder()
                .email(request.getEmail())
                .address(request.getAddress())
                .postcode(request.getPostcode())
                .deliveryStatus(DeliveryStatus.READY)
                .createdAt(LocalDateTime.now())
                .build();
        return order;
    }

    //OrderProductRequest dto -> entity
    private OrderProduct convertToOrderProductEntity(OrderProductRequest orderProductRequest
            ,Order order,Product product){

        OrderProduct orderProduct = OrderProduct.builder()
                .order(order)
                .product(product)
                .quantity(orderProductRequest.getQuantity())
                .build();

        return orderProduct;
    }

    @Transactional
    public OrderResponse updateOrder(Long orderId, CreateOrderRequest request) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));

        if (!order.getDeliveryStatus().equals(DeliveryStatus.READY)) {
            throw new IllegalArgumentException("이미 배송이 시작된 주문입니다.");
        }

        order.setAddress(request.getAddress());
        order.setPostcode(request.getPostcode());
        order.setCreatedAt(LocalDateTime.now());

        order.getOrderProducts().clear();

        // 새로운 상품 추가
        for(OrderProductRequest orderProductRequest : request.getProducts()){
            if (orderProductRequest.getQuantity() <= 0) continue;
            Product product = productRepository.findById(orderProductRequest.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다.!"));

            OrderProduct orderProduct = convertToOrderProductEntity(orderProductRequest,order,product);

            order.addOrderProduct(orderProduct);
        }

        Order savedOrder = orderRepository.save(order);
        savedOrder.calculateTotalPrice();

        return new OrderResponse(savedOrder);
    }

    @Transactional
    public void deleteOrder(Long orderId){
        orderRepository.deleteById(orderId);
    }
}
