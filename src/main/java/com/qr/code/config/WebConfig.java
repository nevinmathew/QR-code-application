package com.qr.code.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for handling static resources and setting cache headers.
 * This class configures the application to leverage browser caching by specifying appropriate headers
 * for static resources. A cache period of one year is set for resources located in the "/static" path.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Adds resource handlers for static resources and sets cache headers.
     *
     * @param registry The registry for configuring resource handlers.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(31536000);
    }
}
