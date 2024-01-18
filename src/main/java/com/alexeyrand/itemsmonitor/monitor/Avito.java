package com.alexeyrand.itemsmonitor.monitor;


import com.alexeyrand.itemsmonitor.api.client.RequestSender;
import com.alexeyrand.itemsmonitor.model.Item;
import com.alexeyrand.itemsmonitor.model.ItemOrderComparator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import static org.openqa.selenium.By.xpath;


public class Avito implements Runnable {

    String Url;
    String chatId;
    //AvitoParser avitoParser = new AvitoParser();
    //WebDriver driver = avitoParser.getDriver();
    public Avito(String Url, String chatId) {
        this.Url = Url;
        this.chatId = chatId;
    }
    @Override
    public void run() {
        RequestSender requestSender = new RequestSender();
        AvitoParser avitoParser = new AvitoParser(requestSender, chatId);
        System.out.println(Thread.currentThread().getName());
        avitoParser.setup();
        avitoParser.openBrowser(Url);
        System.out.println("Открыл");
        while (true) {
            avitoParser.start();
            //System.out.println(Thread.currentThread().getName() + " Старт закончился");
            avitoParser.update();
            //System.out.println(Thread.currentThread().getName() + " Обновление закончилось");
            try {
                Thread.sleep(180000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        //
    }

}
