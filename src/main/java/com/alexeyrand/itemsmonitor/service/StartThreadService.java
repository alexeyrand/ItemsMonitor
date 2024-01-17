package com.alexeyrand.itemsmonitor.service;

import com.alexeyrand.itemsmonitor.monitor.ThreadParser;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;


public class StartThreadService {

    public void go(String url, String chatId) {
        ThreadParser parser = new ThreadParser(url, chatId);
        Thread thread = new Thread(parser);

        thread.start();

        System.out.println(Thread.currentThread().getName());
    }
}
