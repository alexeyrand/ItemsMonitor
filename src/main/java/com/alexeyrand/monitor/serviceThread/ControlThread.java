package com.alexeyrand.monitor.serviceThread;

import com.alexeyrand.monitor.api.client.RequestSender;
import com.alexeyrand.monitor.api.dto.MessageDto;
import com.alexeyrand.monitor.monitor.Avito;
import com.alexeyrand.monitor.repository.ShopRepository;
import com.alexeyrand.monitor.services.ItemService;
import com.alexeyrand.monitor.services.ShopService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
//@AllArgsConstructor
@RequiredArgsConstructor
public class ControlThread {

    private final RequestSender requestSender;
    private final StateThread stateThread;
    private final ItemService itemService;
    private final ShopService shopService;
    private final ShopRepository shopRepository;

    public void startAllThreads(MessageDto messageDto) throws InterruptedException {

        String split1 = "https://www.avito.ru/all/odezhda_obuv_aksessuary/sumki_ryukzaki_i_chemodany-ASgBAgICAUTeArip1gI?cd=1&f=ASgBAgECAUTeArip1gIBRcaaDBV7ImZyb20iOjEwMDAwLCJ0byI6MH0&q=louis+vuitton&s=104&user=1";
        String split2 = "https://www.avito.ru/all/odezhda_obuv_aksessuary/sumki_ryukzaki_i_chemodany-ASgBAgICAUTeArip1gI?cd=1&f=ASgBAgECAUTeArip1gIBRcaaDBV7ImZyb20iOjE1MDAwLCJ0byI6MH0&q=chanel&s=104&user=1";
        String split3 = "https://www.avito.ru/all/odezhda_obuv_aksessuary/sumki_ryukzaki_i_chemodany-ASgBAgICAUTeArip1gI?cd=1&f=ASgBAgECAUTeArip1gIBRcaaDBV7ImZyb20iOjEwMDAwLCJ0byI6MH0&q=prada&s=104&user=1";

        Avito parser1 = new Avito(requestSender, messageDto, stateThread, itemService, shopService, shopRepository);
        parser1.setUrl(split1);
        Avito parser2 = new Avito(requestSender, messageDto, stateThread, itemService, shopService, shopRepository);
        parser2.setUrl(split2);
        Avito parser3 = new Avito(requestSender, messageDto, stateThread, itemService, shopService, shopRepository);
        parser3.setUrl(split3);

        Thread thread1 = new Thread(parser1);
        Thread thread2 = new Thread(parser2);
        Thread thread3 = new Thread(parser3);

        thread1.start();
        Thread.sleep(2000);
        thread2.start();
        Thread.sleep(4000);
        thread3.start();



    }
}
