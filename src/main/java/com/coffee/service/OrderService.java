package com.coffee.service;

import com.coffee.domain.DeliveryStatus;
import com.coffee.domain.Order;
import com.coffee.domain.OrderProduct;
import com.coffee.domain.Product;
import com.coffee.dto.CreateOrderRequest;
import com.coffee.dto.OrderProductRequest;
import com.coffee.dto.OrderResponse;
import com.coffee.exception.InvalidOrderException;
import com.coffee.exception.ProductNotFoundException;
import com.coffee.repository.OrderRepository;
import com.coffee.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request){

        if (request.getProducts() == null || request.getProducts().isEmpty()) {
            throw new InvalidOrderException("하나 이상의 상품을 선택해주세요.");
        }

        Order order = convertToOrderEntity(request);

        for(OrderProductRequest orderProductRequest : request.getProducts()){
            Product product = productRepository.findById(orderProductRequest.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(orderProductRequest.getProductId()));

            OrderProduct orderProduct = convertToOrderProductEntity(orderProductRequest,order,product);

            order.addOrderProduct(orderProduct);
        }

        Order savedOrder = orderRepository.save(order);
        savedOrder.calculateTotalPrice();

        return new OrderResponse(savedOrder);
    }



    public List<OrderResponse> findOrdersByEmail(String email){
        List<Order> orders = orderRepository.findByEmail(email);
        return orders.stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }




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

        for(OrderProductRequest orderProductRequest : request.getProducts()) {

            if (orderProductRequest.getQuantity() <= 0) continue;

            Product product = productRepository.findById(orderProductRequest.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다.!"));

            OrderProduct orderProduct = convertToOrderProductEntity(orderProductRequest, order, product);
            order.addOrderProduct(orderProduct);
        }

        Order savedOrder = orderRepository.save(order);
        savedOrder.calculateTotalPrice();

        return new OrderResponse(savedOrder);
    }


   public Page<OrderResponse> getAllOrdersWithPaging(Pageable pageable) {
       return orderRepository.findAll(pageable)
               .map(OrderResponse::new);
   }

}
