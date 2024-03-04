package com.alexeyrand.monitor.api.factories;

import com.alexeyrand.monitor.api.dto.ItemDto;
import com.alexeyrand.monitor.models.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemDtoFactory {
    public ItemDto makeItemDto(Item item, String chatId) {
        return ItemDto.builder()
                .name(item.getName())
                .price(item.getPrice())
                .href(item.getHref())
                .description(item.getDescription())
                .image(item.getImage())
                .chatId(chatId)
                .shop(item.getShop())
                .build();
    }
}
