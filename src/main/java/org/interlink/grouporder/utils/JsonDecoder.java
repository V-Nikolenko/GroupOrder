package org.interlink.grouporder.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.interlink.grouporder.entity.Product;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class JsonDecoder {
    private JsonObject jsonObject;

    private BigDecimal fullPrice;

    public JsonDecoder(String json) {
        jsonObject = new Gson().fromJson(json, JsonObject.class);
        this.fullPrice = new BigDecimal(jsonObject.get("fullPrice").getAsString());
    }

    public BigDecimal getOrderPrice() {
        return fullPrice;
    }

    public List<Product> rebaseJsonToProduct() {
        JsonArray items = jsonObject.getAsJsonArray("items");
        List<Product> memberOrder = new LinkedList<>();

        for (JsonElement element : items) {
            JsonObject object = element.getAsJsonObject();

            String id = object.get("id").getAsString();
            String name = object.get("name").getAsString();
            String price = object.get("price").getAsString();
            String image = object.get("image").getAsString();
            String count = object.get("count").getAsString();
            String key = object.get("key").getAsString();
            String optionId = object.get("optionId").getAsString();
            String optionValue = object.get("optionValue").getAsString();
            String priceExpirationTime = object.get("priceExpirationTime").getAsString();
            String measure = object.get("measure").getAsString();
            String measureType = object.get("measureType").getAsString();
            String standardPrice = object.get("standardPrice").getAsString();
            String packagePrice = object.get("packagePrice").getAsString();
            String maxCountPositionInPackage = object.get("maxCountPositionInPackage").getAsString();

            Product product = new Product(id, name, price, image, count, key, optionId, optionValue,
                    priceExpirationTime, measure, measureType, standardPrice, packagePrice, maxCountPositionInPackage);

            memberOrder.add(product);
        }

        return memberOrder;
    }
}
