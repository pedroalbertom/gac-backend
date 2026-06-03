package com.gac.api.application.port.in.movement;

import com.gac.api.domain.model.AssetType;

public interface ReleaseFromMaintenanceInputPort {
    void execute(AssetType assetType, Long assetId);
}
