package org.interlink.grouporder.misteram.entity;

import java.util.List;

public class Order {
    private List<Item> items;
    private String name;

    public Order(List<Item> items, String name) {
        this.items = items;
        this.name = name;
    }
}
