package org.interlink.grouporder.entity;

public class Product {
    private boolean force = false;
    private String action = "add";
    private int dishId;
    private Object optionValue;
    private int optionId;
    private String measure;
    private String measureType;
    private int packagePrice;
    private int maxCountPositionInPackage;

    public Product(String id, String name, String price, String image, String count, String key, String optionId,
                   String optionValue, String priceExpirationTime, String measure, String measureType,
                   String standardPrice, String packagePrice, String maxCountPositionInPackage) {

        this.dishId = Integer.parseInt(id);
        this.optionValue = optionValue;
        this.optionId = Integer.parseInt(optionId);
        this.measure = measure;
        this.measureType = measureType;
        this.packagePrice = 0;
        this.maxCountPositionInPackage = Integer.parseInt(maxCountPositionInPackage);
    }
}
