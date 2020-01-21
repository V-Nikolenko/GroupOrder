package org.interlink.grouporder.entity;

import lombok.Getter;

@Getter
public class Product {

    private int dishId;
    private String name;

    public Product(int id, String name) {
        this.dishId = id;
        this.name = name;
    }
}
