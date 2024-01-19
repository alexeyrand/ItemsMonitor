package com.alexeyrand.itemsmonitor.api.controller;

import com.alexeyrand.itemsmonitor.monitor.Avito;
import com.alexeyrand.itemsmonitor.service.StartThreadService;
import org.springframework.web.bind.annotation.*;

@RestController
public class TelegramBotController {
    //AvitoParser parser = new AvitoParser();
    StartThreadService service = new StartThreadService();

    @PostMapping("/start")
    public void startParse(@RequestBody String chatId) {
        String[] split = chatId.split(" ");
        System.out.println("Монитор запущен");
        //Avito avito = new Avito("https://www.avito.ru/moskva/telefony/mobilnye_telefony/apple-ASgBAgICAkS0wA3OqzmwwQ2I_Dc?cd=1&s=104&user=1", chatId);
        service.go("https://www.avito.ru/moskva/telefony/mobilnye_telefony/apple-ASgBAgICAkS0wA3OqzmwwQ2I_Dc?cd=1&s=104&user=1", split[0], split[1]);
       // service.go("https://www.avito.ru/moskva/telefony/mobilnye_telefony/apple/iphone_14-ASgBAgICA0SywA3MjuUQtMANzqs5sMENiPw3?cd=1&s=104&user=1", chatId);
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


    @GetMapping("/test")
    public String startParse() throws InterruptedException {
        System.out.println("Получил");
        Thread.sleep(10000);
        return "Hello";
        }


}
