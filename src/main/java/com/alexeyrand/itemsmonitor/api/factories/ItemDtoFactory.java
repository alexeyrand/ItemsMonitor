package com.alexeyrand.itemsmonitor.api.factories;

import com.alexeyrand.itemsmonitor.api.dto.ItemDto;
import com.alexeyrand.itemsmonitor.model.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemDtoFactory {
    public ItemDto makeItemDto(Item item, String chatId) {
        return ItemDto.builder()
                .name(item.getName())
                .price(item.getPrice())
                .href(item.getHref())
                .chatId(chatId)
                .build();
    }
}
