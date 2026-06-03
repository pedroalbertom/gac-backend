package com.gac.api.domain.service.movement;

import java.security.SecureRandom;

public final class ConfirmationCodeGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();

    private ConfirmationCodeGenerator() {
    }

    public static String generate() {
        return String.format("%04d", RANDOM.nextInt(10_000));
    }
}
