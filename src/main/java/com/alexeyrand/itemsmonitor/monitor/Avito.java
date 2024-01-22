package com.alexeyrand.itemsmonitor.monitor;


import com.alexeyrand.itemsmonitor.api.client.RequestSender;
import com.alexeyrand.itemsmonitor.api.dto.MessageDto;
import com.alexeyrand.itemsmonitor.service.StateThread;


public class Avito implements Runnable{
    RequestSender requestSender = new RequestSender();
    String Url;
    MessageDto messageDto;
    StateThread stateThread;

    public Avito(String Url, MessageDto messageDto, StateThread stateThread) {
        this.Url = Url;
        this.messageDto = messageDto;
        this.stateThread = stateThread;
    }

    @Override
    public void run() {

        Parser avitoParser = new AvitoParser(requestSender, messageDto, stateThread);
        System.out.println(Thread.currentThread().getName());

        avitoParser.setup();
        avitoParser.openBrowser(Url);
//        System.out.println("Открыл");

        while (!Thread.interrupted()) {
            avitoParser.start();
 //           System.out.println("Товаров нет, обновляем");
            try {
                avitoParser.update();
                avitoParser.sleep(120);
            } catch (InterruptedException e) {
 //               System.out.println("Прерываю поток");
                Thread.currentThread().interrupt();
                break;
            }

            if (stateThread.isStopFlag()) {
                try {
                    avitoParser.stop();
                } catch (InterruptedException e) {
 //                   System.out.println("Прерываю поток");
                    break;
                }
                break;
            }
        }
        System.out.println("Останавливаю парсер " + Thread.currentThread().getName() + Thread.currentThread().isAlive());
    }
}
