package com.alexeyrand.itemsmonitor.service;

import com.alexeyrand.itemsmonitor.api.client.RequestSender;
import com.alexeyrand.itemsmonitor.api.dto.MessageDto;
import com.alexeyrand.itemsmonitor.monitor.Avito;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

@Component
@AllArgsConstructor
@RequiredArgsConstructor
public class ControlThread {
    private static final RequestSender requestSender = new RequestSender();
    private static final UrlsHandlerService urlsHandlerService = new UrlsHandlerService();
    private static final String host = "http://localhost:8080/api/v1/status";
    @Autowired
    private StateThread stateThread;

    public void go(MessageDto messageDto) {

        //List<String> urls = urlsHandlerService.getUrls();

//        String[] split1 = urls.get(0).split(" -> ");
//        String[] split2 = urls.get(1).split(" -> ");
//        String[] split3 = urls.get(2).split(" -> ");
        String split1 = "https://www.avito.ru/all/odezhda_obuv_aksessuary/sumki_ryukzaki_i_chemodany-ASgBAgICAUTeArip1gI?cd=1&f=ASgBAgECAUTeArip1gIBRcaaDBV7ImZyb20iOjEwMDAwLCJ0byI6MH0&q=louis+vuitton&s=104&user=1";
        //String split2 = "https://www.avito.ru/all/odezhda_obuv_aksessuary/sumki_ryukzaki_i_chemodany-ASgBAgICAUTeArip1gI?cd=1&f=ASgBAgECAUTeArip1gIBRcaaDBV7ImZyb20iOjE1MDAwLCJ0byI6MH0&q=chanel&s=104&user=";
        //String split3 = "https://www.avito.ru/all/odezhda_obuv_aksessuary/sumki_ryukzaki_i_chemodany-ASgBAgICAUTeArip1gI?cd=1&f=ASgBAgECAUTeArip1gIBRcaaDBV7ImZyb20iOjEwMDAwLCJ0byI6MH0&q=prada&s=104&user=1";

        Avito parser1 = new Avito(split1, messageDto, stateThread);
        //Avito parser2 = new Avito(split2, messageDto, stateThread);
        //Avito parser3 = new Avito(split3, messageDto, stateThread);

        Thread thread1 = new Thread(parser1);
        //Thread thread2 = new Thread(parser2);
        //Thread thread3 = new Thread(parser3);

        thread1.start();
        //thread2.start();
        //thread3.start();

        requestSender.statusRequest(URI.create(host + "?status=start"), messageDto);
    }
}
