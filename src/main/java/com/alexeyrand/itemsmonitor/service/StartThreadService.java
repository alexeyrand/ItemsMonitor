package com.alexeyrand.itemsmonitor.service;

import com.alexeyrand.itemsmonitor.monitor.ThreadParser;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;


public class StartThreadService {
    ThreadParser parser = new ThreadParser("f");
    Thread thread = new Thread(parser);
    @Async
    public void go() {
        thread.start();
        System.out.println(Thread.currentThread().getName());
    }
}
