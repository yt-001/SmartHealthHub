package com.xitian.smarthealthhub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${file.upload-path}")
    private String uploadPath;

    @Value("${file.static-access-path}")
    private String staticAccessPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 根据用户要求，明确映射图片和视频的绝对路径
        // 图片路径 F:\SmartHealthHub\images 对应 URL /uploads/images/**
        registry.addResourceHandler("/uploads/images/**")
                .addResourceLocations("file:F:/SmartHealthHub/images/");

        // 视频路径 F:\SmartHealthHub\videos 对应 URL /uploads/videos/**
        registry.addResourceHandler("/uploads/videos/**")
                .addResourceLocations("file:F:/SmartHealthHub/videos/");

        // 保留通用映射作为兜底，确保其他上传文件也能访问
        // 确保路径以 / 结尾
        String path = uploadPath;
        if (!path.endsWith("/") && !path.endsWith("\\")) {
            path += "/";
        }
        registry.addResourceHandler(staticAccessPath)
                .addResourceLocations("file:" + path);
    }
}
