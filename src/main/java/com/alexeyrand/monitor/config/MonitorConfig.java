package com.alexeyrand.monitor.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@Data
@PropertySource("application.yaml")
public class MonitorConfig {

    //////////////////////////////////////////////////////////////////////////////////////////////// monitor endpoints
//    @Value("${monitor-telegram-bot.endpoint.status}")
//    private String statusEndpoint;



}
