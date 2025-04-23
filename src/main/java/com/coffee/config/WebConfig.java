package com.coffee.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /images/** 경로 → uploads/images/ 실제 폴더에 있는 파일을 제공
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:uploads/images/");
    }
}
