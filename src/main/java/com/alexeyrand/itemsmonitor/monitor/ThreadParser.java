package com.alexeyrand.itemsmonitor.monitor;


import com.alexeyrand.itemsmonitor.api.client.RequestSender;
import com.alexeyrand.itemsmonitor.model.Item;
import com.alexeyrand.itemsmonitor.model.ItemOrderComparator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.web.bind.annotation.RequestBody;

import javax.swing.text.StringContent;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import static org.openqa.selenium.By.xpath;


public class ThreadParser implements Runnable {

    String url;
    String chatId;
    TreeSet<Item> items;

    public ThreadParser(String url, String chatId) {
        this.url = url;
        this.chatId = chatId;
        this.items = new TreeSet<>(new ItemOrderComparator());
    }

    @Override
    public void run() {
        RequestSender requestSender = new RequestSender();
        System.out.println(Thread.currentThread().getName());
        ChromeOptions options = new ChromeOptions();
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
        String[] dates = {"1 минуту назад", "2 минуты назад", "3 минуты назад", "4 минуты назад", "5 минут назад", "Несколько секунд назад"};
        while (true) {
            List<WebElement> selectors = driver.findElements(xpath("//div[@data-marker='item']"));
            for (WebElement e : selectors) {
                Item item = new Item(e);
                System.out.println(item);
                Predicate<Item> isContains = x -> items.contains(x);
                if (!isContains.test(item) && Arrays.asList(dates).contains(item.getDate())) {
                    String body = item.getHref();
                    items.add(item);

                    try {
                        System.out.println(items);
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException ex) {
                        System.out.println(ex.getMessage());
                    }
                    try {
                        requestSender.postRequest(URI.create("http://localhost:8080/item"), body + " " + chatId);
                    } catch (IOException | InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }

                } else {
                    System.out.println("update");
                    driver.navigate().refresh();
                    try {
                        TimeUnit.SECONDS.sleep(4);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
        //driver.quit();
    }
}
