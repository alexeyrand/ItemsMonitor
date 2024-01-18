package com.alexeyrand.itemsmonitor.model;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
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

    public Item(WebElement selector, int order) {
        this.selector = selector;
        this.name = selector.findElement(By.cssSelector("h3[itemprop ='name']")).getText();
        this.id = selector.getAttribute("id").substring(1);
        this.href = selector.findElement(By.cssSelector("a[itemprop ='url']")).getAttribute("href");
        this.price = selector.findElement(By.cssSelector("meta[itemprop ='price']")).getAttribute("content");
        this.date = selector.findElement(By.cssSelector("p[data-marker='item-date']")).getText();
        this.order = order;
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