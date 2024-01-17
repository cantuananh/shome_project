package com.shopme.admin.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //user
        String dirName = "user-photos";
        Path userPhotosDir = Paths.get(dirName);

        String userPhotosPath = userPhotosDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/" + dirName + "/**")
                .addResourceLocations("file:" + userPhotosPath + "/");


//        category
        String categoryImagesName = "category-images";
        Path categoryImagesDir = Paths.get(categoryImagesName);

        String categoryImagePath = categoryImagesDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/" + categoryImagesDir + "/**")
                .addResourceLocations("file:" + categoryImagePath + "/");
    }
}
