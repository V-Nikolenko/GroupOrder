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

}
