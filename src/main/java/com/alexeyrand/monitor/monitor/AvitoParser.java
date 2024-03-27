package com.alexeyrand.monitor.monitor;

import com.alexeyrand.monitor.api.client.RequestSender;
import com.alexeyrand.monitor.api.dto.ItemDto;
import com.alexeyrand.monitor.api.dto.MessageDto;
import com.alexeyrand.monitor.api.factories.ItemDtoFactory;
import com.alexeyrand.monitor.models.Item;
import com.alexeyrand.monitor.models.ItemEntity;
import com.alexeyrand.monitor.models.ShopEntity;
import com.alexeyrand.monitor.repository.ShopRepository;
import com.alexeyrand.monitor.serviceThread.StateThread;
import com.alexeyrand.monitor.services.ItemService;
import com.alexeyrand.monitor.services.ShopService;
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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

import static org.openqa.selenium.By.xpath;

//@Component
//@RequiredArgsConstructor
//@AllArgsConstructor
public class AvitoParser implements Parser {

    private final WebDriver driver;

    private final RequestSender requestSender;
    private final MessageDto messageDto;
    private final ItemDtoFactory itemDtoFactory = new ItemDtoFactory();
    private final JavascriptExecutor jse;
    private final ShopService shopService;
    private final ItemService itemService;
    private final ShopRepository shopRepository;

    StateThread stateThread;
    String[] dates = {"1 минуту назад", "2 минуты назад", "3 минуты назад", "4 минуты назад", "5 минут назад", "Несколько секунд назад"};

    public AvitoParser(RequestSender requestSender, MessageDto messageDto, StateThread stateThread, ItemService itemService, ShopService shopService, ShopRepository shopRepository) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        options.addArguments("--disable-javascript");
        options.addArguments("--incognito");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);

        this.jse = (JavascriptExecutor) driver;
        this.requestSender = requestSender;
        this.stateThread = stateThread;
        this.messageDto = messageDto;
        this.itemService = itemService;
        this.shopService = shopService;
        this.shopRepository = shopRepository;

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
        int y = 0;
        AtomicBoolean flag = new AtomicBoolean(false);
        List<WebElement> selectors = driver.findElements(xpath("//div[@data-marker='item']"));
        Thread.sleep(2000);
        String shop;
        Predicate<String> dateValidate = x -> Arrays.asList(dates).contains(x);

        for (WebElement e : selectors) {
            flag.set(true);
            jse.executeScript("window.scrollBy(0, " + y + 1000 + ")");

            TimeUnit.SECONDS.sleep(2);
            String date = e.findElement(By.cssSelector("p[data-marker='item-date']")).getText();
            if (dateValidate.test(date)) {
                String id = e.getAttribute("id").substring(1);
                if (itemService.getByAvitoId(id).isEmpty()) {
                    try {
                        shop = e.findElement(By.xpath("//*[@id=\"i" + id + "\"]/div/div/div[3]/div/div[1]/div/div[1]/a")).getAttribute("href");
                    } catch (NoSuchElementException NSEE) {
                        shop = "";
                    }
                    shopService.findByName(shop).ifPresentOrElse(
                            (shopEntity) -> flag.set(shopEntity.isBlocked()),
                            () -> flag.set(false)
                    );
                } else {
                    break;
                }
            } else {
                break;
            }

            if (!flag.get()) {
                ItemEntity itemEntity = getItemEntity(e);
                if (shopService.findByName(shop).isEmpty()) {
                    ShopEntity shopEntity = ShopEntity.builder().shopName(shop).build();
                    shopService.save(shopEntity);
                    itemEntity.setShopEntity(shopEntity);
                    itemService.save(itemEntity);
                    ItemDto itemDto = itemDtoFactory.makeItemDto(itemEntity, messageDto.getChatId());
                    requestSender.postItemRequest(URI.create("http://localhost:8080/api/v1/items"), itemDto);

                } else {
                    ShopEntity shopEntity = shopService.findByName(shop).get();
                    itemEntity.setShopEntity(shopEntity);
                    itemService.save(itemEntity);
                    ItemDto itemDto = itemDtoFactory.makeItemDto(itemEntity, messageDto.getChatId());
                    requestSender.postItemRequest(URI.create("http://localhost:8080/api/v1/items"), itemDto);
                }

                driver.manage().deleteAllCookies();
            } else {
                break;
            }
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


    private ItemEntity getItemEntity(WebElement e) {

        String name = e.findElement(By.cssSelector("h3[itemprop ='name']")).getText();
        String id = e.getAttribute("id").substring(1);
        String href = e.findElement(By.cssSelector("a[itemprop ='url']")).getAttribute("href");
        String price = e.findElement(By.cssSelector("meta[itemprop ='price']")).getAttribute("content");
        String date = e.findElement(By.cssSelector("p[data-marker='item-date']")).getText();
        String description;
        String image;
        String shop;
        try {
            description = e.findElement(By.cssSelector("div[class*=item-descriptionStep]")).getText();
            description = (description.length() > 300) ? description.substring(0, 300) : description;
        } catch (NoSuchElementException NSEE) {
            description = "No description";
        }
        try {
            String imageSet = e.findElement(By.xpath("//*[@id=\"i" + id + "\"]/div/div/div[1]/a/div/div/ul/li/div/img")).getAttribute("srcset");
            image = imageSet.split(",")[4].split(" ")[0];
        } catch (NoSuchElementException NSEE) {
            try {
                image = e.findElement(By.cssSelector("img[class*='native-video-thum']")).getAttribute("src");
            } catch (NoSuchElementException NSE) {
                image = "";
            }
        }

        try {
            shop = e.findElement(By.xpath("//*[@id=\"i" + id + "\"]/div/div/div[3]/div/div[1]/div/div[1]/a")).getAttribute("href");
        } catch (NoSuchElementException NSEE) {
            shop = "";
        }

        ItemEntity itemEntity = ItemEntity.builder()
                .name(name)
                .avitoId(id)
                .href(href)
                .price(price)
                .date(date)
                .description(description)
                .image(image)
                .build();


        return itemEntity;
    }

}
