package com.alexeyrand.itemsmonitor.monitor;


import com.alexeyrand.itemsmonitor.model.Item;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.By.xpath;

public class ThreadParser implements Runnable {

    String url;
    public ThreadParser(String url) {
        this.url = url;
    }

    @Override
    public void run() {

        System.out.println(Thread.currentThread().getName());
        ChromeOptions options = new ChromeOptions();;
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        //options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(url);
        List<WebElement> selectors = driver.findElements(xpath("//div[@data-marker='item']"));
        for (WebElement e : selectors) {
        Item item = new Item(e);
        System.out.println(item.getName() + item.getPrice());
        }
    }
}
