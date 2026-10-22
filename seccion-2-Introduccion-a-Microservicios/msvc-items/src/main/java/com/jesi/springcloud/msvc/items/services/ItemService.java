package com.jesi.springcloud.msvc.items.services;

import java.util.List;
import java.util.Optional;

import com.jesi.springcloud.msvc.items.models.ItemDTO;

public interface ItemService {
    List<ItemDTO> findAll();

    Optional<ItemDTO> findById(Long id);


} 