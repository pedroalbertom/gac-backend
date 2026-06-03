package com.gac.api.presentation.dto.request;

import com.gac.api.core.domain.AssetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ExchangeAssetRequest(
        @NotNull Long loanId,
        @NotNull AssetType substituteAssetType,
        @NotNull Long substituteAssetId,
        @NotBlank String defectDescription,
        String room,
        List<String> loanedAccessories) {
}
