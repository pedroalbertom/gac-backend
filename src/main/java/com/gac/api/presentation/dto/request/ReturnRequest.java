package com.gac.api.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ReturnRequest(
        @NotNull Long loanId, boolean hasDefect, String defectDescription, List<String> returnedAccessories) {
}
