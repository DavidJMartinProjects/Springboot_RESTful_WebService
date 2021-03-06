package com.project.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.project.business", "com.project.domain", "com.project.web", "com.project.topten", "com.project.custom.league" })
public class WebProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebProjectApplication.class, args);		
	}
}
