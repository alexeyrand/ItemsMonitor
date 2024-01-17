package com.alexeyrand.itemsmonitor.model;

import java.util.Comparator;

public class ItemOrderComparator implements Comparator<Item> {
    @Override
    public int compare(Item o1, Item o2) {
        return o1.getOrder().compareTo(o2.getOrder());
    }
}
