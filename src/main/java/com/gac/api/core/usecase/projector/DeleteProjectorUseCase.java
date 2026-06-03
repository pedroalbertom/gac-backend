package com.gac.api.core.usecase.projector;

import com.gac.api.core.domain.ItemStatus;
import com.gac.api.core.domain.Projector;
import com.gac.api.core.gateway.ProjectorGateway;

public class DeleteProjectorUseCase {

    private final ProjectorGateway projectorGateway;

    public DeleteProjectorUseCase(ProjectorGateway projectorGateway) {
        this.projectorGateway = projectorGateway;
    }

    public void execute(Long id) {
        Projector projector = projectorGateway.findById(id)
                .orElseThrow(() -> new RuntimeException("Projector not found."));

        if (projector.getStatus() == ItemStatus.ON_LOAN || projector.getStatus() == ItemStatus.RESERVED) {
            throw new RuntimeException("Cannot delete a projector that is reserved or on loan.");
        }

        projectorGateway.deleteById(id);
    }
}
