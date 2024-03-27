package com.alexeyrand.monitor.services;

import com.alexeyrand.monitor.models.ShopEntity;
import com.alexeyrand.monitor.repository.ShopRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;

    public void save(ShopEntity shopEntity) {
        shopRepository.save(shopEntity);
    }

    public void update(Integer shop_id) {
        ShopEntity shopEntity = shopRepository.getById(shop_id);
        shopEntity.setBlocked(true);
        shopRepository.save(shopEntity);
    }

    public List<ShopEntity> getAll() {
        return shopRepository.findAll();
    }


    public Optional<ShopEntity> findByName(String name) {
        return shopRepository.findByShopName(name);
    }

}
