package com.alexeyrand.itemsmonitor.controller;

import com.alexeyrand.itemsmonitor.monitor.AvitoParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    AvitoParser parser = new AvitoParser();


    @GetMapping("/start")
    public void startParse() {
        parser.start();
    }

}
