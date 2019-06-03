package com.mune.system.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configurable
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

    @Value("${upload.img.resourceLocation}")
    private String resourceLocation;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations(resourceLocation);
        super.addResourceHandlers(registry);
    }

}
