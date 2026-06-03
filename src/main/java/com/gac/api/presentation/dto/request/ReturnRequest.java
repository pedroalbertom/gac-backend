package com.gac.api.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

public record ReturnRequest(@NotNull Long loanId, boolean hasDefect, String defectDescription) {
}
