package com.alexeyrand.itemsmonitor.controller;

import com.alexeyrand.itemsmonitor.monitor.AvitoParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TelegramBotController {
    AvitoParser parser = new AvitoParser();


    @GetMapping("/start")
    public void startParse() {
        System.out.println("Запрос пришел. Монитор запущен");
        parser.start();

    }

    @GetMapping("/stop")
    public void stopParse() {
        parser.stop();
    }

}
