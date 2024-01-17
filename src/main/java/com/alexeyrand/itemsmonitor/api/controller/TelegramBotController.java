package com.alexeyrand.itemsmonitor.api.controller;

import com.alexeyrand.itemsmonitor.monitor.AvitoParser;
import com.alexeyrand.itemsmonitor.monitor.Main;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TelegramBotController {
    AvitoParser parser = new AvitoParser();

    @Async
    @GetMapping("/start")
    public void startParse() throws InterruptedException {

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
