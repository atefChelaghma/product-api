package com.profile.profile_data;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Product API",
				version = "v1",
				description = "REST API for creating, retrieving, updating, and deleting products."
		)
)
public class ProfileDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProfileDataApplication.class, args);
	}

}
