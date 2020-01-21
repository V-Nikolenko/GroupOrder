package org.interlink.grouporder.entity;

import lombok.Getter;

@Getter
public class Product {
    private boolean force = false;
    private String action = "add";

    private int dishId;
    private String optionValue;
    private int optionId;
    private String measure;
    private String measureType;
    private int packagePrice;
    private int maxCountPositionInPackage;

    public Product(int id, String optionValue, int optionId, String measure,
                   String measureType, int packagePrice, int maxCountPositionInPackage) {

        this.dishId = id;
        this.optionValue = optionValue;
        this.optionId = optionId;
        this.measure = measure;
        this.measureType = measureType;
        this.packagePrice = packagePrice;
        this.maxCountPositionInPackage = maxCountPositionInPackage;
    }
}
