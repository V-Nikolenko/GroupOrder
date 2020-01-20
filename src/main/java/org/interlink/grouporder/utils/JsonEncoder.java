package org.interlink.grouporder.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonEncoder {

    private JsonEncoder() {
    }

    private static final Gson GSON = new GsonBuilder().create();

    public static <T> String encode(T value) {
        return GSON.toJson(value).replace("\"null\"", "null");
    }
}