package com.alexeyrand.monitor.repository;


import com.alexeyrand.monitor.models.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Integer> {
    Optional<ItemEntity>  findByAvitoId(String id);
}
