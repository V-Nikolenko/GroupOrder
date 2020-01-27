package org.interlink.grouporder.core.data;

import org.interlink.grouporder.core.entity.GroupOrder;
import org.interlink.grouporder.core.entity.MemberOrder;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class DataStorage {

    private static Map<String, GroupOrder> orders = new LinkedHashMap<>();

    public static void addGroupOrder(String code, GroupOrder groupOrder) {
        getOrders().put(code, groupOrder);
    }

    public static void removeGroupOrder(String code) {
        getOrders().remove(code);
    }

    public static GroupOrder getGroupOrder(String code) {
        return Optional.ofNullable(getOrders().get(code)).orElseThrow(
                () -> new IllegalArgumentException("No such key in the database [" + code + "]"));
    }

    public static void addMemberToOrder(String code, MemberOrder memberOrder) {
        if (getOrders().containsKey(code) && memberOrder != null) {
            GroupOrder groupOrder = getOrders().get(code);
            groupOrder.addMemberToGroupOrder(memberOrder);

            getOrders().replace(code, groupOrder);
        }
    }

    public static boolean isContains(String code) {
        return getOrders().containsKey(code);
    }

    public static Map<String, GroupOrder> getOrders() {
        return orders;
    }

}