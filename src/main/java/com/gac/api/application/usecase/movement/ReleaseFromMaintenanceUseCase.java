package com.gac.api.application.usecase.movement;

import com.gac.api.application.port.in.movement.ReleaseFromMaintenanceInputPort;

import com.gac.api.domain.service.movement.*;

import com.gac.api.domain.model.AssetType;
import com.gac.api.domain.port.KeyGateway;
import com.gac.api.domain.port.ProjectorGateway;

public class ReleaseFromMaintenanceUseCase implements ReleaseFromMaintenanceInputPort {

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
