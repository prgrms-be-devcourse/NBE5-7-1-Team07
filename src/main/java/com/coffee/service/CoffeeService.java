package com.coffee.service;

import com.coffee.domain.Coffee;
import com.coffee.dto.CoffeeDto;
import com.coffee.repository.CoffeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoffeeService {

    private final CoffeeRepository coffeeRepository;

    // 전체 커피 목록 조회
    public List<CoffeeDto> findAll() {
        return coffeeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 커피 상세 조회
    public CoffeeDto findById(Long id) {
        Coffee coffee = coffeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 커피 없음: " + id));
        return convertToDto(coffee);
    }

    // 커피 등록
    public void save(CoffeeDto dto) {
        coffeeRepository.save(convertToEntity(dto));
    }

    // 커피 수정
    public void update(Long id, CoffeeDto dto) {
        Coffee coffee = coffeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 커피 없음: " + id));

        coffee.setName(dto.getName());
        coffee.setPrice(dto.getPrice());
        coffee.setDescription(dto.getDescription());

        coffeeRepository.save(coffee);
    }

    // 커피 삭제
    public void delete(Long id) {
        coffeeRepository.deleteById(id);
    }

    // Entity -> DTO 변환
    private CoffeeDto convertToDto(Coffee coffee) {
        return CoffeeDto.builder()
                .id(coffee.getId())
                .name(coffee.getName())
                .price(coffee.getPrice())
                .description(coffee.getDescription())
                .build();
    }

    // DTO -> Entity 변환
    private Coffee convertToEntity(CoffeeDto dto) {
        return Coffee.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .build();
    }
}