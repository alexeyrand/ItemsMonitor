package com.alexeyrand.monitor.repository;


import com.alexeyrand.monitor.models.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<ShopEntity, Integer> {

    Optional<ShopEntity> findByShopName(String name);
}
