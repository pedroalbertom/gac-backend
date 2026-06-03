package com.gac.api.application.usecase.projector;

import com.gac.api.application.port.in.projector.UpdateProjectorInputPort;

import com.gac.api.domain.model.Projector;
import com.gac.api.domain.exception.NotFoundException;
import com.gac.api.application.port.out.ProjectorGateway;

public class UpdateProjectorUseCase implements UpdateProjectorInputPort {

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
