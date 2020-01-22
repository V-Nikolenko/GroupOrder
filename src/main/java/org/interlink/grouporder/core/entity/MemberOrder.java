package org.interlink.grouporder.core.entity;

import java.util.List;

public class MemberOrder {
    private String name;
    private String internetShopURL;
    private List<Product> products;

    public MemberOrder(String name, String internetShopURL, List<Product> products) {
        this.name = name;
        this.internetShopURL = internetShopURL;
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }
}
