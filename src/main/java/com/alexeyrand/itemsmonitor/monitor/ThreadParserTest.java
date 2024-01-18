package com.alexeyrand.itemsmonitor.monitor;


import com.alexeyrand.itemsmonitor.api.client.RequestSender;
import com.alexeyrand.itemsmonitor.model.Item;
import com.alexeyrand.itemsmonitor.model.ItemOrderComparator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import static org.openqa.selenium.By.xpath;


public class ThreadParserTest implements Runnable {


    TreeSet<Item> items = new TreeSet<>(new ItemOrderComparator());

    @Override
    public void run() {
        //RequestSender requestSender = new RequestSender();
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
        driver.get("https://www.avito.ru/moskva/telefony/mobilnye_telefony/apple-ASgBAgICAkS0wA3OqzmwwQ2I_Dc?cd=1&s=104&user=1");
        String[] dates = {"1 минуту назад", "2 минуты назад", "3 минуты назад", "4 минуты назад", "5 минут назад", "Несколько секунд назад"};
       // while (true) {
            List<WebElement> selectors = driver.findElements(xpath("//div[@data-marker='item']"));
            for (WebElement e : selectors) {
                Item item = new Item(e, 11);
                System.out.println(item);
                Predicate<Item> isContains = x -> items.contains(x);
                if (!isContains.test(item) && Arrays.asList(dates).contains(item.getDate())) {
                    //String body = item.getHref();
                    items.add(item);

                    System.out.println(item);

                } else {
                    System.out.println("update");
                    //driver.navigate().refresh();
                    try {
                        TimeUnit.SECONDS.sleep(4);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        //    }
        }
        //driver.quit();
    }
}
