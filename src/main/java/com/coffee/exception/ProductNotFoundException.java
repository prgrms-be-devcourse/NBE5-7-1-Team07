package com.coffee.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("해당 커피 없음: " + id);
    }
}
