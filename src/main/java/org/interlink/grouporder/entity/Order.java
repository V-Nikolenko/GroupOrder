package org.interlink.grouporder.entity;

import java.util.LinkedList;
import java.util.List;

public class Order {
    private List<Product> items = new LinkedList<>();

    public void addProductsToList(List<Product> products) {
        items.addAll(products);
    }

    public List<Product> getItems() {
        return items;
    }
}
