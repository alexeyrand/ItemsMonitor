package com.alexeyrand.itemsmonitor.api.controller;

import com.alexeyrand.itemsmonitor.api.dto.MessageDto;
import com.alexeyrand.itemsmonitor.monitor.Avito;
import com.alexeyrand.itemsmonitor.service.StartThreadService;
import org.springframework.web.bind.annotation.*;

@RestController("api/v1/")
public class TelegramBotController {

    StartThreadService service = new StartThreadService();

    private static final String START = "/start";
    private static final String STOP = "/stop";
    private static final String URLS = "/urls";

    @PostMapping(START)
    public void startParse(@RequestBody String messageDto) {
        System.out.println("Монитор запущен");
        //Avito avito = new Avito("https://www.avito.ru/moskva/telefony/mobilnye_telefony/apple-ASgBAgICAkS0wA3OqzmwwQ2I_Dc?cd=1&s=104&user=1", chatId);
        service.go("https://www.avito.ru/moskva/telefony/mobilnye_telefony/apple-ASgBAgICAkS0wA3OqzmwwQ2I_Dc?cd=1&s=104&user=1", messageDto);
       // service.go("https://www.avito.ru/moskva/telefony/mobilnye_telefony/apple/iphone_14-ASgBAgICA0SywA3MjuUQtMANzqs5sMENiPw3?cd=1&s=104&user=1", chatId);
    }

    @GetMapping(STOP)
    public void stopParse() throws InterruptedException {

    }

    @PostMapping(URLS)
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
