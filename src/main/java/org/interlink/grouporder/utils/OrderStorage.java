package org.interlink.grouporder.utils;

import org.interlink.grouporder.entity.Order;

import java.util.LinkedHashMap;
import java.util.Map;

public class OrderStorage {
    private Map<String, Order> orderStorage = new LinkedHashMap<>();

    public void addOrder(String keyOrder, Order order) {
        getOrderStorage().put(keyOrder, order);
    }

    public void removeOrder(String keyOrder) {
        getOrderStorage().remove(keyOrder);
    }

    public Map<String, Order> getOrderStorage() {
        return orderStorage;
    }

}
