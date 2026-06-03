package com.gac.api.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ReturnRequest(
        @NotNull Long loanId, boolean hasDefect, String defectDescription, List<String> returnedAccessories) {
}
