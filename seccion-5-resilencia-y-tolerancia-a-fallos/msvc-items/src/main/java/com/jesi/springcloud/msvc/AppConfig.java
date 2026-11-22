package com.jesi.springcloud.msvc;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;

@Configuration
public class AppConfig {
    @Bean 
    Customizer<Resilience4JCircuitBreakerFactory> customizerCircuitBreaker() {

        return (factory) -> factory.configureDefault(id -> {
            return new Resilience4JConfigBuilder(id).circuitBreakerConfig(CircuitBreakerConfig
                .custom()
                .slidingWindowSize(10)//ventana de 10 llamadas para ver el estado del circuito
                .failureRateThreshold(50) //Tasa de fallo del 50% para abrir el circuito
                .waitDurationInOpenState(Duration.ofSeconds(10L))//tiempo de duración en estado abierto 10 segundos
                .permittedNumberOfCallsInHalfOpenState(5)
                .build())
                .build();
        });
    }
}
