package com.alexeyrand.monitor.api.controllers;

import com.alexeyrand.monitor.api.client.RequestSender;
import com.alexeyrand.monitor.api.dto.MessageDto;
import com.alexeyrand.monitor.models.ShopEntity;
import com.alexeyrand.monitor.serviceThread.ControlThread;
import com.alexeyrand.monitor.serviceThread.StateThread;
import com.alexeyrand.monitor.services.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MonitorController {


    private final StateThread stateThread;
    private final ControlThread controlThread;
    private final ShopService shopService;
    private final RequestSender requestSender;

    private static final String START = "/start";
    private static final String STOP = "/stop";
    private static final String URLS = "/urls";
    private static final String STATUS = "/status";
    private static final String BLOCK_LIST = "/block/{shop_name}";

    @PostMapping(value = START, consumes = {"application/json"})
    public void startParse(@RequestBody MessageDto messageDto) {
        controlThread.startAllThreads(messageDto);
    }

    @GetMapping(value = STOP)
    public void stopParse() {
        stateThread.setStopFlag(true);
    }

    @GetMapping(value = BLOCK_LIST)
    public void addBlockList(@PathVariable String shop_name) {
        ShopEntity shopEntity = shopService.getByName("https://www.avito.ru/user/" + shop_name + "/profile?src=search_seller_info").get();
        shopEntity.setBlocked(true);
        shopService.save(shopEntity);
        }


    @PostMapping(value = STATUS, consumes = {"application/json"})
    public void getStatus(@RequestBody MessageDto messageDto) {
        requestSender.statusRequest(URI.create("http://localhost:8080/api/v1/status?status=state"), messageDto);
    }
}
