package com.gac.api.application.service.movement;

import org.springframework.stereotype.Service;

import com.gac.api.domain.service.movement.*;

import com.gac.api.domain.model.AssetType;
import com.gac.api.application.repository.KeyRepository;
import com.gac.api.application.repository.ProjectorRepository;

@Service
public class ReleaseFromMaintenanceService {

    private final ProjectorRepository projectorRepository;
    private final KeyRepository keyRepository;

    public ReleaseFromMaintenanceService(ProjectorRepository projectorRepository, KeyRepository keyRepository) {
        this.projectorRepository = projectorRepository;
        this.keyRepository = keyRepository;
    }

    public void execute(AssetType assetType, Long assetId) {
        AssetInventory.releaseFromMaintenance(assetType, assetId, projectorRepository, keyRepository);
    }
}
