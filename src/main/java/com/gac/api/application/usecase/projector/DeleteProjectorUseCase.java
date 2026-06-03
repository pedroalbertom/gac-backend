package com.gac.api.application.usecase.projector;

import com.gac.api.application.port.in.projector.DeleteProjectorInputPort;

import com.gac.api.domain.model.ItemStatus;
import com.gac.api.domain.model.Projector;
import com.gac.api.domain.exception.BusinessRuleException;
import com.gac.api.domain.exception.NotFoundException;
import com.gac.api.domain.port.ProjectorGateway;

public class DeleteProjectorUseCase implements DeleteProjectorInputPort {

    private final ProjectorGateway projectorGateway;

    public DeleteProjectorUseCase(ProjectorGateway projectorGateway) {
        this.projectorGateway = projectorGateway;
    }

    public void execute(Long id) {
        Projector projector = projectorGateway.findById(id)
                .orElseThrow(() -> new NotFoundException("Projector not found."));

        if (projector.getStatus() == ItemStatus.ON_LOAN || projector.getStatus() == ItemStatus.RESERVED) {
            throw new BusinessRuleException("Cannot delete a projector that is reserved or on loan.");
        }

        projectorGateway.deleteById(id);
    }
}
