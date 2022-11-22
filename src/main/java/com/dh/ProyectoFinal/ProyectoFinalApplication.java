package com.dh.ProyectoFinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class ProyectoFinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoFinalApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/odontologos").allowedOrigins("http://127.0.0.1:5500");
				registry.addMapping("/pacientes").allowedOrigins("http://127.0.0.1:5500");
				registry.addMapping("/turnos").allowedOrigins("http://127.0.0.1:5500");
			}
		};
	}
}
