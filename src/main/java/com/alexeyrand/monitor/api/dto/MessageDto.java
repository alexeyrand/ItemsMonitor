package com.alexeyrand.monitor.api.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    private String chatId;
    private Integer messageId;

}
