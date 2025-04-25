package com.coffee.controller;

import com.coffee.dto.OrderResponse;
import com.coffee.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;

    @GetMapping("/admin/orders")
    public String showOrderList(Model model, Pageable pageable) {
        Page<OrderResponse> orders = orderService.getAllOrdersWithPaging(pageable);
        model.addAttribute("orders", orders);
        return "admin/orderList";
    }


}
