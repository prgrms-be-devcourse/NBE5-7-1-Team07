package com.coffee.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 업로드된 이미지에 접근할 수 있는 URL 패턴 설정
        try {
            File dir = new File(uploadDir);
            // 절대 경로로 변환
            String absolutePath = dir.getAbsolutePath() + File.separator;
            System.out.println("리소스 경로: " + absolutePath); // 디버깅용

            String uploadPath = "file:" + dir.getAbsolutePath() + File.separator;
            registry.addResourceHandler("/uploaded-images/**")
                    .addResourceLocations(uploadPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}