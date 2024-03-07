package com.alexeyrand.monitor.monitor;


import com.alexeyrand.monitor.api.client.RequestSender;
import com.alexeyrand.monitor.api.dto.MessageDto;
import com.alexeyrand.monitor.repository.ShopRepository;
import com.alexeyrand.monitor.serviceThread.StateThread;
import com.alexeyrand.monitor.services.ItemService;
import com.alexeyrand.monitor.services.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
//@AllArgsConstructor
@RequiredArgsConstructor
public class Avito implements Runnable {
    private final RequestSender requestSender;
    @Setter
    private String Url;
    private final MessageDto messageDto;
    private final StateThread stateThread;
    private final ItemService itemService;
    private final ShopService shopService;
    private final ShopRepository shopRepository;


//    public Avito(String Url, MessageDto messageDto, StateThread stateThread) {
//        this.Url = Url;
//        this.messageDto = messageDto;
//        this.stateThread = stateThread;
//
//    }

    @Override
    public void run() {

        Parser avitoParser = new AvitoParser(requestSender, messageDto, stateThread, itemService, shopService, shopRepository);
        System.out.println(Thread.currentThread().getName());

        avitoParser.setup();
        avitoParser.openBrowser(Url);

        while (!Thread.interrupted()) {
            avitoParser.start();
//            if (Thread.interrupted()) {
//                break;
//            }
            try {
                avitoParser.update();
                avitoParser.sleep(10);
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
