package com.coffee.controller;

import com.coffee.dto.ProductRequestDto;
import com.coffee.dto.ProductResponseDto;
import com.coffee.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/coffee")
public class ProductController {

    private final ProductService productService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    // 전체 목록 보기
    @GetMapping
    public String list(Model model) {
        List<ProductResponseDto> products = productService.findAll();
        model.addAttribute("products", products);
        return "coffee/list";
    }

    // 제품 등록 폼
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("productRequestDto", new ProductRequestDto());
        return "coffee/add";
    }

    // 제품 등록 처리
    @PostMapping("/add")
    public String add(@ModelAttribute ProductRequestDto productRequestDto,
                      @RequestParam("imageFile") MultipartFile imageFile) {
        productService.save(productRequestDto, imageFile);
        return "redirect:/coffee";
    }

    // 제품 수정 폼
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        ProductResponseDto product = productService.findById(id);
        ProductRequestDto requestDto = productService.convertToRequestDto(product);
        model.addAttribute("productRequestDto", requestDto);
        model.addAttribute("productResponseDto", product);
        return "coffee/edit";
    }

    // 제품 수정 처리
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       @ModelAttribute ProductRequestDto productRequestDto,
                       @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        productService.update(id, productRequestDto, imageFile);
        return "redirect:/coffee";
    }

    // 제품 삭제 처리
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/coffee";
    }

    // 이미지 경로를 위한 메소드
    @ModelAttribute("uploadPath")
    public String uploadPath() {
        return "/uploaded-images/";
    }
}
