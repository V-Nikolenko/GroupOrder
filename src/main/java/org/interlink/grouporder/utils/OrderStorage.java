package org.interlink.grouporder.utils;

import org.interlink.grouporder.entity.Order;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class OrderStorage {
    private static Map<String, Order> orders = new LinkedHashMap<>();

    public void addOrder(String keyOrder, Order order) {
        orders.put(keyOrder, order);
    }

    public void removeOrder(String keyOrder) {
        orders.remove(keyOrder);
    }

    public Order getOrder(String code) {
        return Optional.ofNullable(orders.get(code)).orElseThrow(
                () -> new IllegalArgumentException("No such key in the database [" + code + "]"));
    }


}
