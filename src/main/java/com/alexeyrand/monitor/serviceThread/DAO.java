package com.alexeyrand.monitor.serviceThread;

import com.alexeyrand.monitor.services.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DAO {
    private final ShopService shopService;

    public void test() {
        System.out.println("Я тут" + shopService);
        System.out.println(shopService.getAll());

    }
}
