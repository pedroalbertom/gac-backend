package com.gac.api.core.usecase.movement;

import com.gac.api.core.domain.AssetType;
import com.gac.api.core.gateway.KeyGateway;
import com.gac.api.core.gateway.ProjectorGateway;

public class ReleaseFromMaintenanceUseCase {

    private final ProjectorGateway projectorGateway;
    private final KeyGateway keyGateway;

    public ReleaseFromMaintenanceUseCase(ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        this.projectorGateway = projectorGateway;
        this.keyGateway = keyGateway;
    }

    public void execute(AssetType assetType, Long assetId) {
        AssetInventory.releaseFromMaintenance(assetType, assetId, projectorGateway, keyGateway);
    }
}
