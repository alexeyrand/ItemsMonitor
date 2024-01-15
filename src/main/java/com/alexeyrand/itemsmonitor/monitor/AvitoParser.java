package com.alexeyrand.itemsmonitor.monitor;

import com.alexeyrand.itemsmonitor.model.Item;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.By.xpath;

public class AvitoParser {


    int k = 0;

    public void start() {
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://www.avito.ru/moskva/telefony/mobilnye_telefony/apple-ASgBAgICAkS0wA3OqzmwwQ2I_Dc?cd=1&s=104&user=1");
        List<WebElement> selectors = driver.findElements(xpath("//div[@data-marker='item']"));
        for (WebElement e : selectors) {
            k++;
            Item item = new Item(e);
            System.out.println(item.getName() + item.getPrice());
            try {
                Thread.sleep(3000);
            } catch (Exception ee) {
                System.out.println(ee.getMessage());
            }
            if (k>5)
                driver.quit();
        }
        stop();

    }

    public void stop() {
        System.out.println("stop");
    }

}
