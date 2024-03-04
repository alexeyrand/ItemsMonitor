package com.alexeyrand.monitor.api.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect
public class UrlDto {
    private String name;
    private String url;
}
