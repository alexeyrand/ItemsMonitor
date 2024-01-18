package com.alexeyrand.itemsmonitor.api.controller;

import com.alexeyrand.itemsmonitor.service.StartThreadService;
import org.springframework.web.bind.annotation.*;

@RestController
public class TelegramBotController {
    //AvitoParser parser = new AvitoParser();
    StartThreadService service = new StartThreadService();

    @PostMapping("/start")
    public void startParse(@RequestBody String chatId) {

        System.out.println("Монитор запущен");
        service.go("https://www.avito.ru/moskva/telefony/mobilnye_telefony/apple-ASgBAgICAkS0wA3OqzmwwQ2I_Dc?cd=1&s=104&user=1", chatId);

    }

    @GetMapping("/stop")
    public void stopParse() throws InterruptedException {

    }

    @PostMapping("/url")
    public void getUrl(@RequestBody String urlDto) {
        System.out.println("Я тут");
        System.out.println(urlDto);
        //System.out.println(urlDto.getName());
        //System.out.println(urlDto.getUrl());

    }
}
