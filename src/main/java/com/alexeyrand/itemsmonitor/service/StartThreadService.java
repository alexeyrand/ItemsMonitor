package com.alexeyrand.itemsmonitor.service;

import com.alexeyrand.itemsmonitor.monitor.Avito;
import com.alexeyrand.itemsmonitor.monitor.ThreadParser;
import com.alexeyrand.itemsmonitor.monitor.ThreadParserTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;


public class StartThreadService {

    public void go(String url, String chatId) {
//        ThreadParser parser = new ThreadParser(url, chatId);
//        Thread thread = new Thread(parser);
//        thread.start();

        //Avito avito = new Avito(url, chatId);
        //Thread thread = new Thread(avito);
        //thread.start();

        Avito parser1 = new Avito(url, chatId);
        //Avito parser2 = new Avito(url, chatId);
        Thread thread1 = new Thread(parser1);
        //Thread thread2 = new Thread(parser2);
        thread1.start();
        //thread2.start();

        System.out.println(Thread.currentThread().getName());
    }
}
