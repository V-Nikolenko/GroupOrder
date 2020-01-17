package org.interlink.grouporder.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.interlink.grouporder.entity.Product;

import java.util.List;

public class JsonEncoder {
    private static final Gson GSON = new GsonBuilder().create();

    public String encode(List<Product> products) {
        return GSON.toJson(products).replace("\"null\"","null");
    }
}