package org.interlink.grouporder.core.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class OrderCodeGenerator {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final Base64.Encoder ENCODER = Base64.getEncoder().withoutPadding();

    private OrderCodeGenerator() {
    }


    public static String generateCode() {
        byte[] bytes = new byte[6];
        RANDOM.nextBytes(bytes);

        return ENCODER.encodeToString(bytes).toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
    }
}
