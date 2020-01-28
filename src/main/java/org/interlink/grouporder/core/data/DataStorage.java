package org.interlink.grouporder.core.data;

import org.interlink.grouporder.core.entity.GroupOrder;
import org.interlink.grouporder.core.exceptions.BadRequestException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class DataStorage {

    private DataStorage() {
    }

    private static Map<String, GroupOrder> orders = new LinkedHashMap<>();

    public static void addGroupOrder(String code, GroupOrder groupOrder) {
        if (groupOrder != null) {
            getOrders().put(code, groupOrder);
        } else {
            throw new BadRequestException("Group order is invalid");
        }
    }

    public static void removeGroupOrder(String code) {
        if (code != null) {
            getOrders().remove(code);
        } else {
            throw new BadRequestException("Key is invalid");
        }
    }

    public static GroupOrder getGroupOrder(String code) {
        return Optional.ofNullable(getOrders().get(code)).orElseThrow(
                () -> new IllegalArgumentException("No such key in the database [" + code + "]"));
    }

    public static boolean isContains(String code) {
        return getOrders().containsKey(code);
    }

    public static Map<String, GroupOrder> getOrders() {
        return orders;
    }

}