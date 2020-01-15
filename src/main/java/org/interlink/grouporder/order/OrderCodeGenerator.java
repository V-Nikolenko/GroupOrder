package org.interlink.grouporder.order;

import java.security.SecureRandom;
import java.util.Base64;

public class OrderCodeGenerator {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final Base64.Encoder ENCODER = Base64.getEncoder().withoutPadding();

    String generateOrderCode() {
        byte[] bytes = new byte[6];
        RANDOM.nextBytes(bytes);

        return ENCODER.encodeToString(bytes).toLowerCase().trim();
    }
}
