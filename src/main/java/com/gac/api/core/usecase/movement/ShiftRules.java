package com.gac.api.core.usecase.movement;

import java.time.LocalDate;
import java.time.LocalDateTime;

final class ShiftRules {

    private ShiftRules() {
    }

    static LocalDateTime loanReturnDeadline(LocalDateTime checkedOutAt) {
        LocalDate date = checkedOutAt.toLocalDate();
        int hour = checkedOutAt.getHour();
        if (hour < 12) {
            return date.atTime(12, 0);
        }
        if (hour < 18) {
            return date.atTime(18, 0);
        }
        return date.atTime(23, 0);
    }

    static LocalDateTime reservationExpiry(LocalDateTime createdAt) {
        LocalDate date = createdAt.toLocalDate();
        int hour = createdAt.getHour();
        if (hour < 12) {
            return date.atTime(18, 0);
        }
        if (hour < 18) {
            return date.atTime(23, 0);
        }
        return date.plusDays(1).atTime(12, 0);
    }
}
