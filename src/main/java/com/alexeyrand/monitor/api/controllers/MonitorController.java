package com.alexeyrand.monitor.api.controllers;

import com.alexeyrand.monitor.api.dto.MessageDto;
import com.alexeyrand.monitor.api.dto.UrlDto;
import com.alexeyrand.monitor.serviceThread.ControlThread;
import com.alexeyrand.monitor.serviceThread.DAO;
import com.alexeyrand.monitor.serviceThread.StateThread;
import com.alexeyrand.monitor.serviceThread.UrlsHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class MonitorController {

    @Autowired
    private StateThread stateThread;
    @Autowired
    private ControlThread controlThread;
    private final UrlsHandlerService urlsHandlerService = new UrlsHandlerService();

    private static final String START = "/start";
    private static final String STOP = "/stop";
    private static final String URLS = "/urls";

    @PostMapping(value = START, consumes = {"application/json"})
    public void startParse(@RequestBody MessageDto messageDto) {

        System.out.println("Монитор запущен");
        controlThread.go(messageDto);
    }

    @GetMapping(value = STOP)
    public void stopParse() {
        System.out.println("Команда на остановку потоков");
        stateThread.setStopFlag(true);
    }

    @PostMapping(value = URLS, consumes = {"application/json"})
    public void getUrl(@RequestBody UrlDto urlDto) {
        System.out.println(urlDto);
        urlsHandlerService.setUrls(urlDto);
    }


    @GetMapping("/test")
    public String startParse() throws InterruptedException {
        System.out.println("Получил");
        Thread.sleep(10000);
        return "Hello";
        }
}
