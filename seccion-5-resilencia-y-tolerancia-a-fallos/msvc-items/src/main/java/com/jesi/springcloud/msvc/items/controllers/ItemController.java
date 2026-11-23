package com.jesi.springcloud.msvc.items.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.jesi.springcloud.msvc.items.models.ItemDTO;
import com.jesi.springcloud.msvc.items.models.ProductDTO;
import com.jesi.springcloud.msvc.items.services.ItemService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class ItemController {
    private final Logger logger = LoggerFactory.getLogger(ItemController.class);

    private final ItemService service;
    private final CircuitBreakerFactory cBreakerFactory;

    // Opcion 2 usar @Qualifier("itemServiceWebClient") ItemService service para
    // decirle que implemente este servicio y no el otro de feign
    public ItemController(@Qualifier("itemServiceWebClient") ItemService service, CircuitBreakerFactory cBreakerFactory) {
        this.service = service;
        this.cBreakerFactory = cBreakerFactory;
    }

    @GetMapping
    public List<ItemDTO> list(@RequestParam(name = "name", required = false) String name,
            @RequestHeader(name = "token-request", required = false) String tokenRequest) {
        System.out.println("========== ENTRÓ AL CONTROLLER ==========");

        System.out.println(name);
        System.out.println( tokenRequest);
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) {

        Optional<ItemDTO> item = cBreakerFactory.create("items").run(()-> service.findById(id), e -> {
            System.out.println(e.getMessage()); //camino alternativo cuando el servicio falla
            logger.error(e.getMessage());

            ProductDTO productDTO = new ProductDTO();
            productDTO.setCreateAt(LocalDate.now());
            productDTO.setId(1L);
            productDTO.setName("Camara Sony");
            productDTO.setPrice(500.00);
            return Optional.of(new ItemDTO(productDTO, 5));
        });
                
        
        if (item.isPresent()) {
            return ResponseEntity.ok(item.get());
        }

        // return ResponseEntity.notFound().build();
        return ResponseEntity.status(404)
                .body(Collections.singletonMap("message", "No existe product en el microservicio msv-products"));
    }

}
