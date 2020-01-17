package org.interlink.grouporder.entity;

import java.math.BigDecimal;
import java.util.List;

public class Order {
    private List<Product> items;
    private String name;
    private BigDecimal fullPrice;

    public Order(List<Product> items, String name, BigDecimal fullPrice) {
        this.items = items;
        this.name = name;
        this.fullPrice = fullPrice;
    }

    public List<Product> getAllProducts() {
        return items;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getFullPrice() {
        return fullPrice;
    }

    public void addNewProduct(Product product) {
        this.items.add(product);
    }

}
