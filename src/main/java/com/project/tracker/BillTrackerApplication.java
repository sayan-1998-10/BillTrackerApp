package com.project.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.project.tracker.security.JwtConfig;


@SpringBootApplication
@EnableConfigurationProperties(value = JwtConfig.class)
public class BillTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillTrackerApplication.class, args);
	}

} 
