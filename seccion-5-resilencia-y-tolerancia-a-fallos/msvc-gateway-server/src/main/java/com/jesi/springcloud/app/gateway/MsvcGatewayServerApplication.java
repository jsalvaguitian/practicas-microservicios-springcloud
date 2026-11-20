package com.jesi.springcloud.app.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsvcGatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcGatewayServerApplication.class, args);
	}

}

/* En api gateway reactivo versiones anteriores usaban application.yml
#spring:
  application:
    name: msvc-gateway-server
  cloud:
    gateway:
      routes:
        - id: msvc-product
          uri: lb://msvc-product
          predicates:
            - Path=/api/products/**

        - id: msvc-items
          uri: lb://msvc-items
          predicates:
            - Path=/api/items/**
          filters:
            - StripPrefix=2

*/
