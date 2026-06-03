package com.gac.api.core.usecase.projector;

import com.gac.api.core.domain.Projector;
import com.gac.api.core.gateway.ProjectorGateway;
import java.util.List;

public class ListProjectorsUseCase {

    private final ProjectorGateway projectorGateway;

    public ListProjectorsUseCase(ProjectorGateway projectorGateway) {
        this.projectorGateway = projectorGateway;
    }

    public List<Projector> execute() {
        return projectorGateway.findAll();
    }
}
