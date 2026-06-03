package com.gac.api.application.dto.movement;

import com.gac.api.domain.model.AssetType;
import java.util.List;

public record ExchangeAssetCommand(
        Long loanId,
        AssetType substituteAssetType,
        Long substituteAssetId,
        String defectDescription,
        Long attendantId,
        String room,
        List<String> loanedAccessories) {}
