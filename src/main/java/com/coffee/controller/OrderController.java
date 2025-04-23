package com.coffee.controller;

import com.coffee.dto.CreateOrderRequest;
import com.coffee.dto.OrderResponse;
import com.coffee.repository.ProductRepository;
import com.coffee.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor

public class OrderController {

    private final OrderService orderService;
    private final ProductRepository productRepository;

    // 1. 주문 입력 폼 보여주기 (GET)
    @GetMapping("/order-form")
    public String orderForm(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "orderForm"; // templates/orderForm.html
    }

    // 2. 주문 처리 (POST)
    @PostMapping
    public String createOrder(@ModelAttribute CreateOrderRequest request, Model model) {
        OrderResponse response = orderService.createOrder(request);
        model.addAttribute("order", response);
        return "orderResult"; // templates/orderResult.html
    }
}
