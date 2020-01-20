package org.interlink.grouporder.utils;

import com.google.gson.*;
import org.interlink.grouporder.entity.Product;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class JsonDecoder {
    private String memberName;
    private int memberRestaurant;
    private List<Product> memberProducts;
    private BigDecimal fullPrice;

    public JsonDecoder(String json) {
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);

        this.memberName = jsonObject.get("name").getAsString();
        JsonObject memberOrder = jsonObject.getAsJsonObject("order");

        this.memberRestaurant = memberOrder.get("companyId").getAsInt();
        this.fullPrice = memberOrder.get("fullPrice").getAsBigDecimal();
        this.memberProducts = rebaseJsonToProduct(memberOrder.get("items").getAsJsonArray());

    }

    private List<Product> rebaseJsonToProduct(JsonArray items) {
        List<Product> products = new LinkedList<>();

        for (JsonElement element : items) {
            JsonObject object = element.getAsJsonObject();

            int id = object.get("id").getAsInt();
            int optionId = object.get("optionId").getAsInt();
            String optionValue = object.get("optionValue").getAsString();
            String measure = object.get("measure").getAsString();
            String measureType = object.get("measureType").getAsString();
            JsonNull packagePrice = object.get("packagePrice").getAsJsonNull();
            int maxCountPositionInPackage = object.get("maxCountPositionInPackage").getAsInt();

            Product product = new Product(id, optionId, optionValue, measure, measureType, packagePrice,
                    maxCountPositionInPackage);

            products.add(product);
        }
        return products;
    }

    public String getName() {
        return memberName;
    }

    public int getRestaurant() {
        return memberRestaurant;
    }

    public List<Product> getProducts() {
        return memberProducts;
    }

    public BigDecimal getPrice() {
        return fullPrice;
    }
}
