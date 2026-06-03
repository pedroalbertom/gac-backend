package com.gac.api.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ConfirmLoanRequest(
        @NotNull Long reservationId,
        @NotBlank String confirmationCode,
        @NotBlank String room,
        List<String> loanedAccessories) {
}
