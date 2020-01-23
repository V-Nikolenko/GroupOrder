package org.interlink.grouporder.core.entity;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Product {

    private int id;
    private String name;
    private BigDecimal price;
    private int count;

    public Product(int id, String name, BigDecimal price, int count) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
    }
}
