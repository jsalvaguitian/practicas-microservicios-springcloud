package com.example.jesi.springcloud.msvc.products.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.jesi.springcloud.msvc.products.entities.Product;
import com.example.jesi.springcloud.msvc.products.services.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    public List<Product> list() {
        //opcion 2
        //ResponseEntity.ok(this.productService.findAll());

        //Sinonimo a opcion 2
        //ResponseEntity.status(200).body(this.productService.findAll());

        return this.productService.findAll();
    }
        

    @GetMapping("/{id}")
    public ResponseEntity<Product> details(@PathVariable Long id) throws InterruptedException {

        if(id.equals(10L)){
            throw new IllegalStateException("Producto no encontrado");
        }

        if(id.equals(7L)){
            TimeUnit.SECONDS.sleep(5L);
        }

        
        Optional<Product> productSearched = productService.findById(id);

        if(productSearched.isPresent()){
            return ResponseEntity.ok(productSearched.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    
    



    

}
