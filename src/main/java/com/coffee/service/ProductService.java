package com.coffee.service;

import com.coffee.domain.Product;
import com.coffee.dto.ProductRequestDto;
import com.coffee.dto.ProductResponseDto;
import com.coffee.exception.FileStorageException;
import com.coffee.exception.ProductNotFoundException;
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


    public List<ProductResponseDto> findAll() {
        return productRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }


    public ProductResponseDto findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return convertToResponseDto(product);
    }


    public void save(ProductRequestDto dto, MultipartFile imageFile) {
        Product product = convertToEntity(dto);

        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = saveImage(imageFile);
            product.setImagePath(imagePath);
        }

        productRepository.save(product);
    }


    public void update(Long id, ProductRequestDto dto, MultipartFile imageFile) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());

        if (imageFile != null && !imageFile.isEmpty()) {
            deleteImageIfExists(product.getImagePath());
            String imagePath = saveImage(imageFile);
            product.setImagePath(imagePath);
        }

        productRepository.save(product);
    }


    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        deleteImageIfExists(product.getImagePath());
        productRepository.deleteById(id);
    }


    private String saveImage(MultipartFile imageFile) {
        if (imageFile == null || imageFile.isEmpty()) {
            return null;
        }

        try {
            File directory = new File(uploadDir);
            if (!directory.exists() && !directory.mkdirs()) {
                throw new FileStorageException("이미지 저장 디렉토리 생성 실패", null);
            }

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
            throw new FileStorageException("이미지 저장 실패", e);
        }
    }


    private void deleteImageIfExists(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            Path filePath = Paths.get(uploadDir, imagePath);
            try {
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                System.err.println("이미지 삭제 실패: " + e.getMessage());
            }
        }
    }


    private ProductResponseDto convertToResponseDto(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .imagePath(product.getImagePath())
                .build();
    }


    private Product convertToEntity(ProductRequestDto dto) {
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .build();
    }


    public ProductRequestDto convertToRequestDto(ProductResponseDto responseDto) {
        return ProductRequestDto.builder()
                .id(responseDto.getId())
                .name(responseDto.getName())
                .price(responseDto.getPrice())
                .description(responseDto.getDescription())
                .build();
    }
}
