package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication	// To enable auto configurations and to scan all the classes available in child directory
/*
 * Here we have configured SwaggerDocumentation which can be accessed in localhost by using below URL:
 * http://localhost:8181/swagger-ui.html
 * or
 * http://localhost:9393/customer-service/swagger-ui.html (through API gateway)
 */
@EnableSwagger2
/*
 * We are registering this DamcoAssessment micro service in configured
 * discovery-server micro service in which we can see our all the
 * registered micro services that can be accessed in localhost by using below
 * URL: http://localhost:8761/
 */
@EnableEurekaClient
public class CustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}

}
