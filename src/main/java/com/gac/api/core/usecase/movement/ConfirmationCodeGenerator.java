package com.gac.api.core.usecase.movement;

import java.security.SecureRandom;

final class ConfirmationCodeGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();

    private ConfirmationCodeGenerator() {
    }

    static String generate() {
        return String.format("%04d", RANDOM.nextInt(10_000));
    }
}
