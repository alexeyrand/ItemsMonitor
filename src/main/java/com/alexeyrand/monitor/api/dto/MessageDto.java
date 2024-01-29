package com.alexeyrand.monitor.api.dto;

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
