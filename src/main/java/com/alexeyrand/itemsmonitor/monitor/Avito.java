package com.alexeyrand.itemsmonitor.monitor;


import com.alexeyrand.itemsmonitor.api.client.RequestSender;
import com.alexeyrand.itemsmonitor.service.StateThread;


public class Avito implements Runnable{
    RequestSender requestSender = new RequestSender();
    String Url;
    String chatId;
    StateThread stateThread;

    public Avito(String Url, String chatId, StateThread stateThread) {
        this.Url = Url;
        this.chatId = chatId;
        this.stateThread = stateThread;
    }

    @Override
    public void run() {

        Parser avitoParser = new AvitoParser(requestSender, chatId, stateThread);
        System.out.println(Thread.currentThread().getName());

        avitoParser.setup();
        avitoParser.openBrowser(Url);
        System.out.println("Открыл");

        while (stateThread.startFlag) {
            avitoParser.start();
            avitoParser.update();
            try {
                Thread.sleep(180000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (!stateThread.stopFlag) {
                avitoParser.stop();
            }
        }

        //
    }

}
