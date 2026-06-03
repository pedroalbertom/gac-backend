package com.gac.api.application.dto.movement;

import java.util.List;

public record ConfirmLoanCommand(
        Long reservationId,
        String confirmationCode,
        Long attendantId,
        String room,
        List<String> loanedAccessories) {}
