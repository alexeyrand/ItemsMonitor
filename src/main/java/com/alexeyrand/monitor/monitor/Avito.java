package com.alexeyrand.monitor.monitor;


import com.alexeyrand.monitor.api.client.RequestSender;
import com.alexeyrand.monitor.api.dto.MessageDto;
import com.alexeyrand.monitor.service.StateThread;


public class Avito implements Runnable {
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

        while (!Thread.interrupted()) {
            //System.out.println("Начало цикла " + Thread.currentThread().getName());
            avitoParser.start();
            if (Thread.interrupted()) {
                break;
            }
            try {
                avitoParser.update();
                avitoParser.sleep(27);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            if (stateThread.isStopFlag()) {
                try {
                    avitoParser.stop();
                    break;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
        System.out.println("Останавливаю парсер " + Thread.currentThread().getName() + Thread.currentThread().isAlive());
    }
}
