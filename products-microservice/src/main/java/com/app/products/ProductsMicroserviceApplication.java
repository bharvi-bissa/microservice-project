package com.app.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProductsMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsMicroserviceApplication.class, args);
	}

}
