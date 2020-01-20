package org.interlink.grouporder.entity;

import java.math.BigDecimal;
import java.util.List;

public class OrderMember {
    private String name;
    private int restaurant;
    private List<Product> products;
    private BigDecimal price;

    public OrderMember(String name, int restaurant, List<Product> products, BigDecimal price) {
        this.name = name;
        this.restaurant = restaurant;
        this.products = products;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getRestaurant() {
        return restaurant;
    }

    public List<Product> getProducts() {
        return products;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
