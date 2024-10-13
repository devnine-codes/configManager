package com.aice.conf;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@Configuration
public class MultipartConfig {
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(1024)); // 최대 업로드 파일 크기 (10MB)
        factory.setMaxRequestSize(DataSize.ofMegabytes(1024)); // 최대 요청 크기 (10MB)
        return factory.createMultipartConfig();
    }
}