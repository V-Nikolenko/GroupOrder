package org.interlink.grouporder.utils;

import com.google.gson.*;
import org.interlink.grouporder.entity.Product;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class JsonDecoder {
    private List<Product> memberOrder;
    private BigDecimal fullPrice;
    private String memberName;

    public void decode(String json) {
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);

        this.fullPrice = new BigDecimal(jsonObject.get("fullPrice").getAsString());
        this.memberName = jsonObject.get("name").getAsString();
        this.memberOrder = rebaseJsonToProduct(jsonObject);
    }

    private List<Product> rebaseJsonToProduct(JsonObject jsonObject) {
        JsonArray items = jsonObject.getAsJsonArray("items");
        memberOrder = new LinkedList<>();

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

            memberOrder.add(product);
        }
        return memberOrder;
    }

    public BigDecimal getPrice() {
        return fullPrice;
    }

    public String getName() {
        return memberName;
    }

    public List<Product> getProducts() {
        return memberOrder;
    }
}
