package com.gac.api.core.usecase.projector;

import com.gac.api.core.domain.Projector;
import com.gac.api.core.exception.NotFoundException;
import com.gac.api.core.gateway.ProjectorGateway;

public class UpdateProjectorUseCase {

    private final ProjectorGateway projectorGateway;

    public UpdateProjectorUseCase(ProjectorGateway projectorGateway) {
        this.projectorGateway = projectorGateway;
    }

    public Projector execute(Long id, Projector updatedData) {
        Projector existing = projectorGateway.findById(id)
                .orElseThrow(() -> new NotFoundException("Projector not found."));

        existing.setBrand(updatedData.getBrand());
        existing.setModel(updatedData.getModel());
        existing.setAssetTag(updatedData.getAssetTag());
        existing.setSerialNumber(updatedData.getSerialNumber());

        return projectorGateway.save(existing);
    }
}
