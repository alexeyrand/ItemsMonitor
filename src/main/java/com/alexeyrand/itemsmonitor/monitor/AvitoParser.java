package com.alexeyrand.itemsmonitor.monitor;

import com.alexeyrand.itemsmonitor.api.client.RequestSender;
import com.alexeyrand.itemsmonitor.api.dto.ItemDto;
import com.alexeyrand.itemsmonitor.api.dto.MessageDto;
import com.alexeyrand.itemsmonitor.api.factories.ItemDtoFactory;
import com.alexeyrand.itemsmonitor.model.Item;
import com.alexeyrand.itemsmonitor.service.StateThread;
import lombok.SneakyThrows;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import static org.openqa.selenium.By.xpath;


public class AvitoParser implements Parser {

    private final WebDriver driver;

    private final RequestSender requestSender;
    private final MessageDto messageDto;
    private final ItemDtoFactory itemDtoFactory = new ItemDtoFactory();
    StateThread stateThread;
    HashSet<String> items = new HashSet<>();
    int order = 1;
    String[] dates = {"1 минуту назад", "2 минуты назад", "3 минуты назад", "4 минуты назад", "5 минут назад", "Несколько секунд назад"};

    public AvitoParser(RequestSender requestSender, MessageDto messageDto, StateThread stateThread) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        //options.addArguments("--headless");
        driver = new ChromeDriver(options);
        this.requestSender = requestSender;
        this.stateThread = stateThread;
        this.messageDto = messageDto;
    }

    public void setup() {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void update() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " обновляется...");
        driver.navigate().refresh();
    }

    public void openBrowser(String URL) {
        driver.get(URL);

        //driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.END);


    }

    @SneakyThrows
    public void start() {
        int y = 0;
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        List<WebElement> selectors = driver.findElements(xpath("//div[@data-marker='item']"));
        for (WebElement e : selectors) {
            jse.executeScript("window.scrollBy(0, " + y + 1000 + ")");
            if (stateThread.isStopFlag()) {
                stop();
                break;
            }

            TimeUnit.SECONDS.sleep(8);
            Item item = new Item(e, order++);
            Predicate<String> isContains = x -> items.contains(x);
            //System.out.println(item.getDate() + "   " + Arrays.asList(dates).contains(item.getDate()));

            if (!isContains.test(item.getId()) && Arrays.asList(dates).contains(item.getDate())) {
//                String name = item.getName();
//                String image = item.getImage();
                System.out.println(item.getDate());


                items.add(item.getId());
                if (items.size() > 50) {
                    items = new HashSet<>();
                    System.gc();
                }

                ItemDto itemDto = itemDtoFactory.makeItemDto(item, messageDto.getChatId());
                try {
                    requestSender.postItemRequest(URI.create("http://localhost:8080/api/v1/items"), itemDto);
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                break;
            }

            if (!Arrays.asList(dates).contains(item.getDate()))
                break;
        }
    }


    public void stop() throws InterruptedException {
        System.out.println("stop");
        driver.quit();
        Thread.currentThread().interrupt();

    }

    @Override
    public void sleep(long sec) throws InterruptedException {
        TimeUnit.SECONDS.sleep(sec);
    }
}
