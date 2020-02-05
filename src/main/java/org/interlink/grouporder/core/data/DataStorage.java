package org.interlink.grouporder.core.data;

import lombok.Getter;
import lombok.Setter;
import org.interlink.grouporder.core.entity.GroupOrder;
import org.interlink.grouporder.core.utils.OrderCodeGenerator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
public class DataStorage {

    private DataStorage() {
    }

    private static Map<String, GroupOrder> orders = new LinkedHashMap<>();

    public static String addGroupOrder(String restaurantName, String restaurantUrl) {
        String code = generateUniqueCode();

        orders.put(code, new GroupOrder(code, restaurantName, restaurantUrl));

        return code;
    }

    public static GroupOrder getGroupOrder(String code) {
        orders.keySet().forEach(System.out::println);
        System.out.println("\nandrew pidor\n");
        return Optional.ofNullable(orders.get(code)).orElseThrow(
                () -> new IllegalArgumentException("No such key in the database [" + code + "]"));
    }

    public static boolean isContains(String code) {
        return orders.containsKey(code);
    }

    private static String generateUniqueCode() {
        String code;
        do {
            code = OrderCodeGenerator.generateCode();
        } while (DataStorage.isContains(code));

        return code;
    }
}