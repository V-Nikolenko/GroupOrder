package org.interlink.grouporder.entity;

import com.google.gson.JsonNull;

public class Product {
    private boolean force = false;
    private String action = "add";

    private int dishId;
    private int optionId;
    private String optionValue;
    private String measure;
    private String measureType;
    private String packagePrice;
    private int maxCountPositionInPackage;

    public Product(int id, int optionId, String optionValue, String measure,
                   String measureType, JsonNull packagePrice, int maxCountPositionInPackage) {

        this.dishId = id;
        this.optionId = optionId;
        this.optionValue = optionValue;
        this.measure = measure;
        this.measureType = measureType;
        this.packagePrice = packagePrice.toString();
        this.maxCountPositionInPackage = maxCountPositionInPackage;
    }
}
