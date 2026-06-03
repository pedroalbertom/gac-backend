package com.gac.api.application.usecase.projector;

import com.gac.api.application.port.in.projector.ListProjectorsInputPort;

import com.gac.api.domain.model.Projector;
import com.gac.api.application.port.out.ProjectorGateway;
import java.util.List;

public class ListProjectorsUseCase implements ListProjectorsInputPort {

    private final ProjectorGateway projectorGateway;

    public ListProjectorsUseCase(ProjectorGateway projectorGateway) {
        this.projectorGateway = projectorGateway;
    }

    public List<Projector> execute() {
        return projectorGateway.findAll();
    }
}
