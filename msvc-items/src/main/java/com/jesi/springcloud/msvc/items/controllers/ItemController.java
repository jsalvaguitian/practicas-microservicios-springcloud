package com.jesi.springcloud.msvc.items.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.jesi.springcloud.msvc.items.models.ItemDTO;
import com.jesi.springcloud.msvc.items.services.ItemService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController 
public class ItemController {
    private  final ItemService service;

    //Opcion 2 usar @Qualifier("itemServiceWebClient") ItemService service para decirle que implemente este servicio y no el otro de feign
    public ItemController(@Qualifier("itemServiceWebClient") ItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<ItemDTO> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) {
        
        Optional<ItemDTO> item = service.findById(id);
        if (item.isPresent()) {
            return  ResponseEntity.ok(item.get());   
        }

       // return ResponseEntity.notFound().build();
       return ResponseEntity.status(404).body(Collections.singletonMap("message", "No existe product en el microservicio msv-products"));
    }
    
    

    

}
