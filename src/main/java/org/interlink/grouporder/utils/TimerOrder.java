package org.interlink.grouporder.utils;

import org.interlink.grouporder.entity.OrderStorage;

public class TimerOrder {
    private static final Long TIME = 2 * 60 * 60 * 1000L;

    public static void runTimer(String key) {
        try {
            Thread.sleep(TIME);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        OrderStorage.removeOrder(key);
    }
}
