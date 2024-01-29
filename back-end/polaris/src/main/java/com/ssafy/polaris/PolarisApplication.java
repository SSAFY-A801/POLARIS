package com.ssafy.polaris;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
// @SpringBootApplication
@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class PolarisApplication {

	public static void main(String[] args) {
		SpringApplication.run(PolarisApplication.class, args);


	}

}
