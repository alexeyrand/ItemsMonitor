package com.alexeyrand.monitor.monitor;

import com.alexeyrand.monitor.api.client.RequestSender;
import com.alexeyrand.monitor.api.dto.ItemDto;
import com.alexeyrand.monitor.api.dto.MessageDto;
import com.alexeyrand.monitor.api.factories.ItemDtoFactory;
import com.alexeyrand.monitor.models.Item;
import com.alexeyrand.monitor.service.StateThread;
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
    private final JavascriptExecutor jse;

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
        options.addArguments("--disable-javascript");

        options.addArguments("--headless");
        driver = new ChromeDriver(options);

        this.jse = (JavascriptExecutor) driver;
        this.requestSender = requestSender;
        this.stateThread = stateThread;
        this.messageDto = messageDto;
    }

    public void setup() {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void update() {
        driver.navigate().refresh();
    }

    public void openBrowser(String URL) {
        driver.get(URL);
    }

    @SneakyThrows
    public void start() {
        //int y = 0;
        List<WebElement> selectors = driver.findElements(xpath("//div[@data-marker='item']"));
        Thread.sleep(8000);
        jse.executeScript("window.scrollBy(0, " + 3800 + ")");
        for (WebElement e : selectors) {

            if (stateThread.isStopFlag()) {
                stop();
                break;
            }

            TimeUnit.SECONDS.sleep(4);
            Item item = new Item(e, order++);
            Predicate<String> isContains = x -> items.contains(x);

            if (!isContains.test(item.getId()) && Arrays.asList(dates).contains(item.getDate())) {
                driver.manage().deleteAllCookies();
                items.add(item.getId());
                if (items.size() > 15) {
                    items = new HashSet<>();
                    System.gc();
                }

                System.out.println(item.getShop());
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

    public void stop() {
        System.out.println("stop");
        driver.quit();
        Thread.currentThread().interrupt();

    }

    @Override
    public void sleep(long sec) throws InterruptedException {
        TimeUnit.SECONDS.sleep(sec);
    }
}
