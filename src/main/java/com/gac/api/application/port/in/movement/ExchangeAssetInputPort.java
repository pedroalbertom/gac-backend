package com.gac.api.application.port.in.movement;

import com.gac.api.domain.model.AssetType;
import com.gac.api.domain.model.Movement;
import com.gac.api.domain.model.User;
import java.util.List;

public interface ExchangeAssetInputPort {
    Movement execute(Long loanId, AssetType substituteAssetType, Long substituteAssetId, String defectDescription, User attendant, String room, List<String> loanedAccessories);
}
