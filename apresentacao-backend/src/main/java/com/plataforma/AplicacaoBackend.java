package com.plataforma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan(basePackages = {
    "com.plataforma.controllers",
    "com.plataforma.persistencia.jpa"
})
@EntityScan(basePackages = {
    "com.plataforma.persistencia.jpa.evento",
    "com.plataforma.persistencia.jpa.usuario"
})
@EnableJpaRepositories(basePackages = {
    "com.plataforma.persistencia.jpa.evento",
    "com.plataforma.persistencia.jpa.usuario"
})
public class AplicacaoBackend {

    public static void main(String[] args) {
        SpringApplication.run(AplicacaoBackend.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
            }
        };
    }
}
