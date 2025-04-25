package com.coffee.controller;

import com.coffee.domain.Order;
import com.coffee.domain.OrderProduct;
import com.coffee.domain.Product;
import com.coffee.dto.CreateOrderRequest;
import com.coffee.dto.OrderResponse;
import com.coffee.repository.OrderRepository;
import com.coffee.repository.ProductRepository;
import com.coffee.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;


    // 1. 주문 입력 폼 보여주기
    @GetMapping("/orders-form")
    public String orderForm(Model model) {
        model.addAttribute("products", orderService.findAll());
        return "orderForm";
    }

    // 2. 주문 처리
    @PostMapping("/orders")
    public String createOrder(@ModelAttribute CreateOrderRequest request, Model model) {
        OrderResponse response = orderService.createOrder(request);
        model.addAttribute("order", response);
        return "orderResult";
    }

    //3. 주문 조회
    @GetMapping("/orders/search")
    public String showSearchForm(){
        return "orderSearchForm";
    }

    @PostMapping("/orders/search")
    public String searchOrders(@RequestParam String email,Model model){
        List<OrderResponse> orderResponses = orderService.findOrdersByEmail(email);
        model.addAttribute("orders",orderResponses);
        return "orderSearchResult";
    }

    @GetMapping("/orders/{orderId}/edit")
    public String showEditForm(@PathVariable Long orderId, Model model) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));

        List<Product> products = productRepository.findAll();

        // 주문에 이미 추가된 상품이 있는지 확인하여 상품 목록에 반영
        for (Product product : products) {
            boolean isProductInOrder = order.getOrderProducts().stream()
                    .anyMatch(orderProduct -> orderProduct.getProduct().getId().equals(product.getId()));

            if (!isProductInOrder) {
                order.addOrderProduct(new OrderProduct(product, 0));
            }
        }
        // 모델에 주문과 전체 상품 목록 추가
        model.addAttribute("order", order);
        model.addAttribute("products", products);
        return "orderEditForm";
    }

    @PostMapping("/orders/{orderId}/edit")
    public String updateOrder(@PathVariable Long orderId, @ModelAttribute CreateOrderRequest request, Model model) {
        OrderResponse updatedOrder = orderService.updateOrder(orderId, request);
        model.addAttribute("order", updatedOrder);
        return "orderEditComplete"; // 수정된 주문 결과 페이지로 이동
    }


    @PostMapping("/orders/{orderId}/delete")
    public String deleteOrder(@PathVariable Long orderId) {
        // 주문 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));

        // 배송 상태가 'ready'일 때만 삭제 가능
        if ("READY".equals(order.getDeliveryStatus())) {
            orderRepository.deleteById(orderId);
        }
        return "orderDeleteComplete";
    }
}
