package org.interlink.grouporder.core.data;

import lombok.Getter;
import lombok.Setter;
import org.interlink.grouporder.core.entity.GroupOrder;
import org.interlink.grouporder.core.exceptions.BadRequestException;
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

    public static String addGroupOrder() {
        String code;

        do {
            code = OrderCodeGenerator.generateCode();
        } while (DataStorage.isContains(code));

        orders.put(code, new GroupOrder(code));

        return code;
    }

    public static void removeGroupOrder(String code) {
        if (code != null) {
            orders.remove(code);
        } else {
            throw new BadRequestException("Key is invalid");
        }
    }

    public static GroupOrder getGroupOrder(String code) {
        return Optional.ofNullable(orders.get(code)).orElseThrow(
                () -> new IllegalArgumentException("No such key in the database [" + code + "]"));
    }

    public static boolean isContains(String code) {
        return orders.containsKey(code);
    }
}