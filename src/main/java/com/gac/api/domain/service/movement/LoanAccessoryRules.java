package com.gac.api.domain.service.movement;

import com.gac.api.domain.model.AssetType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public final class LoanAccessoryRules {

    private LoanAccessoryRules() {
    }

    public static List<String> requireMatchingReturn(
            AssetType assetType, List<String> loanedAccessories, List<String> returnedAccessories) {
        if (assetType != AssetType.PROJECTOR || loanedAccessories == null || loanedAccessories.isEmpty()) {
            return List.of();
        }

        if (returnedAccessories == null || returnedAccessories.isEmpty()) {
            throw new RuntimeException("Returned accessories are required for projector loans with accessories.");
        }

        Set<String> expected = normalize(loanedAccessories);
        Set<String> returned = normalize(returnedAccessories);

        if (!expected.equals(returned)) {
            for (String accessory : expected) {
                if (!returned.contains(accessory)) {
                    throw new RuntimeException("Missing returned accessory: " + accessoryLabel(loanedAccessories, accessory));
                }
            }
            throw new RuntimeException("Returned accessories do not match loaned accessories.");
        }

        return new ArrayList<>(returnedAccessories);
    }

    private static Set<String> normalize(List<String> accessories) {
        Set<String> normalized = new HashSet<>();
        for (String accessory : accessories) {
            if (accessory == null || accessory.isBlank()) {
                continue;
            }
            normalized.add(accessory.trim().toLowerCase(Locale.ROOT));
        }
        return normalized;
    }

    private static String accessoryLabel(List<String> originalValues, String normalizedValue) {
        for (String value : originalValues) {
            if (value != null && value.trim().toLowerCase(Locale.ROOT).equals(normalizedValue)) {
                return value.trim();
            }
        }
        return normalizedValue;
    }
}
