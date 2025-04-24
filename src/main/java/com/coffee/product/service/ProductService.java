package com.coffee.product.service;

import com.coffee.product.dto.GetProduct;
import com.coffee.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<GetProduct> getList() {
        return productRepository.findAll()
                                .stream()
                                .map(GetProduct::from)
                                .toList();
    }
}
