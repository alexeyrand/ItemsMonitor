package com.alexeyrand.monitor.monitor;


import com.alexeyrand.monitor.api.client.RequestSender;
import com.alexeyrand.monitor.api.dto.MessageDto;
import com.alexeyrand.monitor.serviceThread.DAO;
import com.alexeyrand.monitor.serviceThread.StateThread;
import com.alexeyrand.monitor.services.ShopService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

//@Component
//@AllArgsConstructor
//@RequiredArgsConstructor
public class Avito implements Runnable {
    RequestSender requestSender;
    private final String Url;
    private final MessageDto messageDto;
    private final StateThread stateThread;
    private final DAO dao;


    public Avito(String Url, MessageDto messageDto, StateThread stateThread, DAO dao) {
        this.Url = Url;
        this.messageDto = messageDto;
        this.stateThread = stateThread;
        this.dao = dao;
    }

    @Override
    public void run() {

        Parser avitoParser = new AvitoParser(requestSender, messageDto, stateThread, dao);
        System.out.println(Thread.currentThread().getName());

        avitoParser.setup();
        avitoParser.openBrowser(Url);

        while (!Thread.interrupted()) {
            avitoParser.start();
            if (Thread.interrupted()) {
                break;
            }
            try {
                avitoParser.update();
                avitoParser.sleep(30);
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
