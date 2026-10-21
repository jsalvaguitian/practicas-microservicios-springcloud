package com.example.jesi.springcloud.msvc.products.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jesi.springcloud.msvc.products.entities.Product;
import com.example.jesi.springcloud.msvc.products.repositories.ProductRepository;

@Service 
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final Environment environment;

    public ProductServiceImpl(ProductRepository repository, Environment environment) {
        this.repository = repository;
        this.environment = environment;
    }

    @Override
    public List<Product> findAll() {
        //sin balanceo de carga
        //return (List<Product>) repository.findAll();

        //Con balanceo de carga
        return ((List<Product>) repository.findAll()).stream().map(p ->{
            p.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
            return  p;
        }).collect(Collectors.toList());
    }

    @Override
    public Optional<Product> findById(Long id) {
        //return repository.findById(id);
        return repository.findById(id).map(p->{
             p.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
            return  p;
        });

    }

}
