package com.alexeyrand.monitor.services;

import com.alexeyrand.monitor.models.ItemEntity;
import com.alexeyrand.monitor.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void save(ItemEntity itemEntity) {
        itemRepository.save(itemEntity);
    }

    public List<ItemEntity> getAll() {
        return itemRepository.findAll();
    }



}
