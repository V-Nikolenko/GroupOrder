package org.interlink.grouporder.core.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberOrder {

    private String name;
    private String email;
    private String products;
    private int restaurantId;
    private int orderPrice;

    public MemberOrder(String name, String email, String products, int restaurantId, int orderPrice) {
        this.name = name;
        this.email = email;
        this.products = products;
        this.restaurantId = restaurantId;
        this.orderPrice = orderPrice;
    }

    public MemberOrder() {
    }
}
