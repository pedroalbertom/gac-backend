package com.gac.api.core.usecase.projector;

import com.gac.api.core.domain.Projector;
import com.gac.api.core.exception.NotFoundException;
import com.gac.api.core.gateway.ProjectorGateway;

public class GetProjectorByIdUseCase {

    private final ProjectorGateway projectorGateway;

    public GetProjectorByIdUseCase(ProjectorGateway projectorGateway) {
        this.projectorGateway = projectorGateway;
    }

    public Projector execute(Long id) {
        return projectorGateway.findById(id).orElseThrow(() -> new NotFoundException("Projector not found."));
    }
}
