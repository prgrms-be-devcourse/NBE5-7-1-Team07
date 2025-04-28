package com.coffee.controller;

import com.coffee.domain.DeliveryStatus;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;



    @GetMapping("/orders-form")
    public String orderForm(Model model) {
        model.addAttribute("products", orderService.findAll());
        return "orderForm";
    }




    @PostMapping("/orders")
    public String createOrder(@ModelAttribute CreateOrderRequest request, Model model) {
        OrderResponse response = orderService.createOrder(request);
        model.addAttribute("order", response);
        return "orderResult";
    }


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

        Map<Long, Integer> productQuantities = new HashMap<>();
        for (OrderProduct op : order.getOrderProducts()) {
            productQuantities.put(op.getProduct().getId(), op.getQuantity());
        }

        model.addAttribute("order", order);
        model.addAttribute("products", products);
        model.addAttribute("productQuantities", productQuantities);
        return "orderEditForm";
    }

    @PostMapping("/orders/{orderId}/edit")
    public String updateOrder(@PathVariable Long orderId, @ModelAttribute CreateOrderRequest request, Model model) {
        OrderResponse updatedOrder = orderService.updateOrder(orderId, request);
        model.addAttribute("order", updatedOrder);
        return "orderEditComplete";
    }


    @PostMapping("/orders/{orderId}/delete")
    public String deleteOrder(@PathVariable Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));

        if (DeliveryStatus.READY.equals(order.getDeliveryStatus())) {
            orderRepository.deleteById(orderId);
        }

        return "orderDeleteComplete";
    }
}
