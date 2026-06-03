package com.gac.api.application.usecase.projector;

import com.gac.api.application.port.in.projector.GetProjectorByIdInputPort;

import com.gac.api.domain.model.Projector;
import com.gac.api.domain.exception.NotFoundException;
import com.gac.api.domain.port.ProjectorGateway;

public class GetProjectorByIdUseCase implements GetProjectorByIdInputPort {

    private final ProjectorGateway projectorGateway;

    public GetProjectorByIdUseCase(ProjectorGateway projectorGateway) {
        this.projectorGateway = projectorGateway;
    }

    public Projector execute(Long id) {
        return projectorGateway.findById(id).orElseThrow(() -> new NotFoundException("Projector not found."));
    }
}
