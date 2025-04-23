package com.coffee.service;

import com.coffee.domain.Product;
import com.coffee.dto.ProductDto;
import com.coffee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    // 전체 커피 목록 조회
    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 커피 상세 조회
    public ProductDto findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 커피 없음: " + id));
        return convertToDto(product);
    }

    // 커피 등록 (파일 업로드 기능 수정)
    public void save(ProductDto dto, MultipartFile imageFile) {
        // 이미지 파일이 있을 경우에만 저장 처리
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = saveImage(imageFile);
            dto.setImagePath(imagePath);
            System.out.println("저장된 이미지 경로: " + imagePath); // 디버깅용
        }

        productRepository.save(convertToEntity(dto));
    }


    // 커피 수정 (파일 업로드 기능 추가)
    public void update(Long id, ProductDto dto, MultipartFile imageFile) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 커피 없음: " + id));

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());

        // 새 이미지가 업로드되었을 경우에만 이미지 경로를 변경
        if (imageFile != null && !imageFile.isEmpty()) {
            // 기존 이미지가 있다면 삭제
            deleteImageIfExists(product.getImagePath());
            String imagePath = saveImage(imageFile);
            product.setImagePath(imagePath);
        }

        productRepository.save(product);
    }

    // 커피 삭제 (이미지 함께 삭제)
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 커피 없음: " + id));

        // 이미지 삭제
        deleteImageIfExists(product.getImagePath());

        productRepository.deleteById(id);
    }

    // 이미지 저장 메소드 수정
    private String saveImage(MultipartFile imageFile) {
        if (imageFile == null || imageFile.isEmpty()) {
            return null;
        }

        try {
            File directory = new File(uploadDir);
            if (!directory.exists() && !directory.mkdirs()) {
                throw new RuntimeException("이미지 저장 디렉토리 생성 실패");
            }

            // 확장자 추출
            String originalFilename = imageFile.getOriginalFilename();
            String extension = "";
            int dotIndex = originalFilename.lastIndexOf('.');
            if (dotIndex >= 0) {
                extension = originalFilename.substring(dotIndex);
            }

            String uniqueFileName = UUID.randomUUID().toString() + extension;

            Path filePath = Paths.get(directory.getAbsolutePath(), uniqueFileName);
            Files.write(filePath, imageFile.getBytes());

            return uniqueFileName;
        } catch (IOException e) {
            throw new RuntimeException("이미지 저장 실패: " + e.getMessage(), e);
        }
    }


    // 이미지 삭제 메소드
    private void deleteImageIfExists(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            Path filePath = Paths.get(uploadDir, imagePath);
            try {
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                // 삭제 실패 로그 처리
                System.err.println("이미지 삭제 실패: " + e.getMessage());
            }
        }
    }

    // Entity -> DTO 변환
    private ProductDto convertToDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .imagePath(product.getImagePath())
                .build();
    }

    // DTO -> Entity 변환
    private Product convertToEntity(ProductDto dto) {
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .imagePath(dto.getImagePath())
                .build();
    }
}