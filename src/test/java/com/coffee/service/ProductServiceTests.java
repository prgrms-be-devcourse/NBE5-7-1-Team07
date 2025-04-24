package com.coffee.service;

import com.coffee.domain.Product;
import com.coffee.dto.ProductRequestDto;
import com.coffee.dto.ProductResponseDto;
import com.coffee.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductServiceBasicTests {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("상품을 저장")
    void saveProduct() throws Exception {
        ProductRequestDto dto = ProductRequestDto.builder()
                .name("에스프레소")
                .price(2500)
                .description("샷만내림")
                .build();


        productService.save(dto, null);


        Product saved = productRepository.findAll().get(0);
        assertThat(saved.getName()).isEqualTo("에스프레소");
        assertThat(saved.getPrice()).isEqualTo(2500);
        assertThat(saved.getDescription()).isEqualTo("샷만내림");
    }

    @Test
    @DisplayName("상품을 수정")
    void updateProduct() throws Exception {
        Product saved = productRepository.save(
                Product.builder()
                        .name("카푸치노")
                        .price(3200)
                        .description("우유 거품 몽글몽글")
                        .imagePath(null)
                        .build()
        );

        ProductRequestDto dto = ProductRequestDto.builder()
                .name("플랫화이트")
                .price(3300)
                .description("라뗴보다 우유 적게 들어간음료")
                .build();

        productService.update(saved.getId(), dto, null);

        Product updated = productRepository.findById(saved.getId()).orElseThrow();
        assertThat(updated.getName()).isEqualTo("플랫화이트");
        assertThat(updated.getPrice()).isEqualTo(3300);
        assertThat(updated.getDescription()).isEqualTo("라뗴보다 우유 적게 들어간음료");
    }

    @Test
    @DisplayName("상품을 삭제")
    void deleteProduct() throws Exception {
        Product saved = productRepository.save(
                Product.builder()
                        .name("모카")
                        .price(3400)
                        .description("초코에 커피맛")
                        .imagePath(null)
                        .build()
        );

        productService.delete(saved.getId());

        boolean exists = productRepository.existsById(saved.getId());
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("상품을 조회")
    void findProduct() throws Exception {
        Product saved = productRepository.save(
                Product.builder()
                        .name("아인슈패너")
                        .price(3100)
                        .description("아메에 크림")
                        .imagePath(null)
                        .build()
        );


        ProductResponseDto foundDto = productService.findById(saved.getId());

        assertThat(foundDto.getName()).isEqualTo("아인슈패너");
        assertThat(foundDto.getPrice()).isEqualTo(3100);
        assertThat(foundDto.getDescription()).isEqualTo("아메에 크림");
    }
}