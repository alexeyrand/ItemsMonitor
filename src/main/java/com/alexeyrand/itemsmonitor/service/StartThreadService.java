package com.alexeyrand.itemsmonitor.service;

import com.alexeyrand.itemsmonitor.api.client.RequestSender;
import com.alexeyrand.itemsmonitor.api.dto.MessageDto;
import com.alexeyrand.itemsmonitor.monitor.Avito;

import java.net.URI;


public class StartThreadService {
    RequestSender requestSender = new RequestSender();
    public void go(String url, String messageDto) {
//
//

        //Avito avito = new Avito(url, chatId);
        //Thread thread = new Thread(avito);
        //thread.start();

        //Avito parser1 = new Avito(url, chatId);
        //Avito parser2 = new Avito(url, chatId);
        //Thread thread1 = new Thread(parser1);
        //Thread thread2 = new Thread(parser2);
        //thread1.start();
        //thread2.start();

        requestSender.statusRequest(URI.create("http://localhost:8080/api/v1/status"), messageDto);
        System.out.println(Thread.currentThread().getName());
    }
}
