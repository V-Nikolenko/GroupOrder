package org.interlink.grouporder.core.entity;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Product {
    private int id;
    private String name;
    private BigDecimal price;
    private int count;
}