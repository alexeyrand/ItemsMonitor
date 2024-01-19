package com.alexeyrand.itemsmonitor.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    private String chatId;
    private Integer messageId;

}
