package com.alexeyrand.itemsmonitor.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("application.yaml")
public class MonitorConfig {

    @Value("${monitor.urls.file.name}")
    private String urlsFileName;

}
