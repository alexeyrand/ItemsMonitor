package com.alexeyrand.monitor.models;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Objects;

/** Класс, характеризующий конкретный товар.  */
@Getter
@Setter
public class Item implements Comparable<Item> {
    WebDriver driver;
    WebElement selector;
    private final String id;
    private final String name;
    private final String href;
    private final String price;
    private final String date;
    private final Integer order;
    private final String description;
    private String image;

    public Item(WebElement selector, int order) {
        this.selector = selector;
        this.name = selector.findElement(By.cssSelector("h3[itemprop ='name']")).getText();
        this.id = selector.getAttribute("id").substring(1);
        this.href = selector.findElement(By.cssSelector("a[itemprop ='url']")).getAttribute("href");
        this.price = selector.findElement(By.cssSelector("meta[itemprop ='price']")).getAttribute("content");
        this.date = selector.findElement(By.cssSelector("p[data-marker='item-date']")).getText();
        this.order = order;
        this.description = selector.findElement(By.cssSelector("div[class*=item-descriptionStep]")).getText();
        try {
            //System.out.println("Пробую достать изображение");
            String imageSet = selector.findElement(By.xpath("//*[@id=\"i" + this.id + "\"]/div/div/div[1]/a/div/div/ul/li/div/img")).getAttribute("srcset");
            this.image = imageSet.split(",")[4].split(" ")[0];
            //System.out.println("Достал изображение");
        } catch (NoSuchElementException NSEE) {
            try {//System.out.println("Пробую достать видео");
                this.image = selector.findElement(By.cssSelector("img[class*='native-video-thum']")).getAttribute("src");
            } catch (NoSuchElementException NSE) {
                this.image = "";
                //System.out.println("Достал видео");
            }
        }
        //  Optional<String> imageOptional = Optional.ofNullable(selector.findElement(By.cssSelector("[class*='photo-slider-image']")).getAttribute("setSrc"));
//try {
//    this.image = imageOptional.orElse("No photo");
//} catch (NoSuchElementException NSEE) {
//    System.out.println(NSEE.getMessage());

//}
        //count++;
    }

    @Override
    public int compareTo(Item o) {
        return this.id.compareTo(o.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(this.id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", order='" + order + '\'' +
                '}';
    }

}