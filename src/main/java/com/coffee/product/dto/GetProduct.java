package com.coffee.product.dto;

import com.coffee.product.domain.Product;

public record GetProduct(long product_id, String name, long price, String image) {

    public static GetProduct from(Product product) {

        return new GetProduct(
                product.getProduct_id(),
                product.getName(),
                product.getPrice(),
                product.getImage()
        );
    }
}
