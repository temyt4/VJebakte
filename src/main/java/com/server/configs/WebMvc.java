package com.server.configs;

import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerViewResolver;

import java.io.IOException;

/**
 * created by xev11
 */

@EnableWebFlux
@Configuration
public class WebMvc implements WebFluxConfigurer {

    private Logger logger = LoggerFactory.getLogger(WebMvc.class);

    @Value("${upload.path}")
    private String uploadPath;



    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPaths("classpath:/templates/");
        return configurer;
    }


    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.freeMarker().suffix(".ftl");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("file:///" + uploadPath + "/");
        registry.addResourceHandler("/").addResourceLocations("classpath:/templates/");
    }
}
