package org.interlink.grouporder.entity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class OrderStorage {

    private static Map<String, Order> orders = new LinkedHashMap<>();

    public static void addOrder(String keyOrder, Order order) {
        orders.put(keyOrder, order);
    }

    public static void removeOrder(String keyOrder) {
        orders.remove(keyOrder);
    }

    public static Order getOrder(String code) {
        return Optional.ofNullable(orders.get(code)).orElseThrow(
                () -> new IllegalArgumentException("No such key in the database [" + code + "]"));

    }

    public static boolean isContains(String key) {
        return orders.containsKey(key);
    }
}
