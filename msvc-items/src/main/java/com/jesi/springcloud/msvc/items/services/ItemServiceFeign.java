package com.jesi.springcloud.msvc.items.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jesi.springcloud.msvc.items.clients.ProductFeignClientHttp;
import com.jesi.springcloud.msvc.items.models.ItemDTO;

@Service
public class ItemServiceFeign implements ItemService {

    private ProductFeignClientHttp clientHttp;

    public ItemServiceFeign(ProductFeignClientHttp clientHttp) {
        this.clientHttp = clientHttp;
    }

    @Override
    public List<ItemDTO> findAll() {
        return clientHttp.findAll()
                .stream()
                .map(
                        product -> new ItemDTO(product,
                                new Random().nextInt(10) + 1))
                .collect(Collectors.toList());

    }

    @Override
    public Optional<ItemDTO> findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

}
