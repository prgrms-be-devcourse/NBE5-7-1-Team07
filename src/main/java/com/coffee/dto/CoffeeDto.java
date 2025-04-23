package com.coffee.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoffeeDto {

    private Long id;
    private String name;
    private int price;
    private String description;
    //private String imagePath;
}
