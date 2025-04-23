package com.coffee.controller;


import com.coffee.dto.CoffeeDto;
import com.coffee.service.CoffeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/coffee")
public class CoffeeController {

    private final CoffeeService coffeeService;

    // 전체 목록 보기
    @GetMapping
    public String list(Model model) {
        List<CoffeeDto> coffees = coffeeService.findAll();
        model.addAttribute("coffees", coffees);
        return "coffee/list"; // templates/coffee/list.html
    }

    // 커피 등록 폼
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("coffeeDto", new CoffeeDto());
        return "coffee/add"; // templates/coffee/add.html
    }

    // 커피 등록 처리
    @PostMapping("/add")
    public String add(@ModelAttribute CoffeeDto coffeeDto) {
        coffeeService.save(coffeeDto);
        return "redirect:/coffee";
    }

    // 커피 수정 폼
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        CoffeeDto coffee = coffeeService.findById(id);
        model.addAttribute("coffeeDto", coffee);
        return "coffee/edit"; // templates/coffee/edit.html
    }

    // 커피 수정 처리
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute CoffeeDto coffeeDto) {
        coffeeService.update(id, coffeeDto);
        return "redirect:/coffee";
    }

    // 커피 삭제 처리
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        coffeeService.delete(id);
        return "redirect:/coffee";
    }
}
