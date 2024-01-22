package com.alexeyrand.itemsmonitor.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private  String id;
    private  String name;
    private  String href;
    private  String price;
    private  String date;
    private  Integer order;
    private String chatId;
}
