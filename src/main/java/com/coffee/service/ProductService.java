package com.coffee.service;

import com.coffee.dto.GetProduct;
import com.coffee.repository.ProductRepository;
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
