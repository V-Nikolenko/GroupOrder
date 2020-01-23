package org.interlink.grouporder.core.data;

import org.interlink.grouporder.core.entity.GroupOrder;
import org.interlink.grouporder.core.entity.MemberOrder;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class DataStorage {

    private static Map<String, GroupOrder> orders = new LinkedHashMap<>();

    public static void addGroupOrder(String code, GroupOrder groupOrder) {
        orders.put(code, groupOrder);
    }

    public static void removeGroupOrder(String code) {
        orders.remove(code);
    }

    public static GroupOrder getGroupOrder(String code) {
        return Optional.ofNullable(orders.get(code)).orElseThrow(
                () -> new IllegalArgumentException("No such key in the database [" + code + "]"));
    }

    public static void addMemberToOrder(String code, MemberOrder memberOrder) {
        if (orders.containsKey(code) && memberOrder != null) {
            GroupOrder groupOrder = orders.get(code);
            groupOrder.addMemberToGroupOrder(memberOrder);

            orders.replace(code, groupOrder);
        }
    }

    public static boolean isContains(String code) {
        return orders.containsKey(code);
    }
}