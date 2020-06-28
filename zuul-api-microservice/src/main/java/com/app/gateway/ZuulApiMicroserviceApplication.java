package com.app.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class ZuulApiMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulApiMicroserviceApplication.class, args);
	}

}
