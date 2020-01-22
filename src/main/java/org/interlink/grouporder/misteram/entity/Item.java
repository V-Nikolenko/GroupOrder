package org.interlink.grouporder.misteram.entity;

public class Item {
    private int id;
    private String name;
    private int count;
    private int price;

    public Item(int id, String name, int count, int price) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.price = price;
    }
}
