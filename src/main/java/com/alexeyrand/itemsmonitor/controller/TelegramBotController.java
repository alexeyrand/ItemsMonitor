package com.alexeyrand.itemsmonitor.controller;

import com.alexeyrand.itemsmonitor.monitor.AvitoParser;
import com.alexeyrand.itemsmonitor.monitor.Main;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TelegramBotController {
    AvitoParser parser = new AvitoParser();


    @GetMapping("/start")
    public void startParse() {
        System.out.println("Запрос пришел. Монитор запущен");
        //parser.start();
        Main.startThread();

    }

    @GetMapping("/stop")
    public void stopParse() throws InterruptedException {
        System.out.println("Начал ждать");
        Thread.sleep(4000);
        System.out.println(Thread.currentThread().getName());
        System.out.println("Закончил ждать");
        parser.stop();
    }

}
