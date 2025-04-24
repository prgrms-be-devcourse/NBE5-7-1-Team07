package com.coffee.controller;

import com.coffee.dto.CreateOrderRequest;
import com.coffee.dto.OrderResponse;
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
}
