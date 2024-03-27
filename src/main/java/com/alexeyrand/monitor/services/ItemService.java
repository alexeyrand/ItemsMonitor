package com.alexeyrand.monitor.services;

import com.alexeyrand.monitor.models.ItemEntity;
import com.alexeyrand.monitor.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void save(ItemEntity itemEntity) {
        itemRepository.save(itemEntity);
    }

    public List<ItemEntity> getAll() {
        return itemRepository.findAll();
    }

    public Optional<ItemEntity> getById(String id) {
        return itemRepository.findById(Integer.getInteger(id));
    }

    public Optional<ItemEntity> getByAvitoId(String id) {
        return itemRepository.findByAvitoId(id);
    }



}
