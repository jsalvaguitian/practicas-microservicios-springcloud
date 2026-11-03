package com.jesi.springcloud.app.gateway.filters;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;


@Component
public class SampleGlobalFilter implements GlobalFilter, Ordered {
    private final Logger logger = LoggerFactory.getLogger(SampleGlobalFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        logger.info("Ejecutando el filtro antes del request PRE");

        // Creamos un nuevo exchange con el header token
        ServerWebExchange modifiedExchange = exchange.mutate()
                .request(r -> r.headers(h -> h.add("token", "abcdefg")))
                .build();

        return chain.filter(modifiedExchange).then(Mono.fromRunnable(() -> {

            logger.info("Ejecutando filtro POST response");

            //Opcion 1
            String token = modifiedExchange.getRequest().getHeaders().getFirst("token");
            if (token != null) {
                logger.info("token: " + token);
                modifiedExchange.getResponse().getHeaders().add("token", token);// Agregamos el token al header de la respuesta

            } 


            //Opcion 2: forma reactiva de hacerlo usando Optional
            Optional.ofNullable(modifiedExchange.getRequest().getHeaders().getFirst("token"))
                    .ifPresent(value -> {
                        logger.info("token2: " + value);
                        modifiedExchange.getResponse().getHeaders().add("token2", value);// Agregamos el token al header de la respuesta

                    });

            modifiedExchange.getResponse().getCookies().add(
                    "color",
                    ResponseCookie.from("color", "red").build()
            );

            modifiedExchange.getResponse().getHeaders()
                    .setContentType(MediaType.TEXT_PLAIN);
        }));
    }
    @Override
    public int getOrder() {
        return 100;
    }
}
