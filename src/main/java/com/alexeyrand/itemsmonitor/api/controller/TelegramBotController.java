package com.alexeyrand.itemsmonitor.api.controller;

import com.alexeyrand.itemsmonitor.monitor.AvitoParser;
import com.alexeyrand.itemsmonitor.monitor.Main;
import com.alexeyrand.itemsmonitor.service.StartThreadService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

@RestController
public class TelegramBotController {
    //AvitoParser parser = new AvitoParser();
    StartThreadService service = new StartThreadService();

    @PostMapping("/start")
    public void startParse(@RequestBody String chatId) throws InterruptedException {

        System.out.println("Запрос пришел. Монитор запущен");
        service.go("https://www.avito.ru/moskva/telefony/mobilnye_telefony/apple-ASgBAgICAkS0wA3OqzmwwQ2I_Dc?cd=1&s=104&user=1", chatId);

    }

    @GetMapping("/stop")
    public void stopParse() throws InterruptedException {

    }

}
