package org.interlink.grouporder.entity;

import java.math.BigDecimal;
import java.util.List;

public class OrderMember {
    private String name;
    private List<Product> products;
    private BigDecimal price;

    public OrderMember(String name, List<Product> products, BigDecimal price) {
        this.name = name;
        this.products = products;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
