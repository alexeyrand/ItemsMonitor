package com.alexeyrand.itemsmonitor.monitor;

import com.alexeyrand.itemsmonitor.api.client.RequestSender;
import com.alexeyrand.itemsmonitor.model.Item;
import com.alexeyrand.itemsmonitor.service.StateThread;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.sql.Time;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import static org.openqa.selenium.By.xpath;


public class AvitoParser implements Parser {

    private WebDriver driver;
    private RequestSender requestSender;
    private String chatId;
    StateThread stateThread;
    HashSet<String> items = new HashSet<>();
    int order = 1;
    String[] dates = {"1 минуту назад", "2 минуты назад", "3 минуты назад", "4 минуты назад", "5 минут назад", "Несколько секунд назад"};



    public AvitoParser(RequestSender requestSender, String chatId, StateThread stateThread) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        driver = new ChromeDriver(options);
        this.requestSender = requestSender;
        this.stateThread = stateThread;
        this.chatId = chatId;
    }

    public void setup () {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public void update () {
        //System.out.println(Thread.currentThread().getName() + " Обновляем");
        driver.navigate().refresh();
    }

    public void openBrowser (String URL){
        driver.get(URL);
    }

    @SneakyThrows
    public void start () {
        List<WebElement> selectors = driver.findElements(xpath("//div[@data-marker='item']"));
        for (WebElement e : selectors) {
            TimeUnit.SECONDS.sleep(2);
            Item item = new Item(e, order++);
            Predicate<String> isContains = x -> items.contains(x);
            if (!isContains.test(item.getId()) && Arrays.asList(dates).contains(item.getDate())) {
                String body = item.getHref();
                items.add(item.getId());
                if (items.size() > 10) {
                    items = new HashSet<>();
                    System.gc();
                }
                System.out.println(Thread.currentThread().getName() + " " + item.getName());
                try {
                    requestSender.postRequest(URI.create("http://localhost:8080/item"), body + " " + chatId);
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                //System.out.println(Thread.currentThread().getName() + "Новых товаров нет");
                break;
            }

            if (!Arrays.asList(dates).contains(item.getDate()))
                break;
        }
    }


    public void stop() {
        System.out.println("stop");
        stateThread.stopFlag = false;
    }

}
