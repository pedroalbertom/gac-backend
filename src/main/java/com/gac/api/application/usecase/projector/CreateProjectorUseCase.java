package com.gac.api.application.usecase.projector;

import com.gac.api.application.port.in.projector.CreateProjectorInputPort;

import com.gac.api.domain.model.ItemStatus;
import com.gac.api.domain.model.Projector;
import com.gac.api.domain.exception.BusinessRuleException;
import com.gac.api.domain.exception.ConflictException;
import com.gac.api.application.port.out.ProjectorGateway;

public class CreateProjectorUseCase implements CreateProjectorInputPort {

    private final ProjectorGateway projectorGateway;

    public CreateProjectorUseCase(ProjectorGateway projectorGateway) {
        this.projectorGateway = projectorGateway;
    }

    public Projector execute(Projector newProjector) {
        projectorGateway.findByAssetTag(newProjector.getAssetTag())
                .ifPresent(p -> {
                    throw new ConflictException("A projector with this asset tag already exists.");
                });

        if (newProjector.getBrand() == null || newProjector.getAssetTag() == null) {
            throw new BusinessRuleException("Brand and asset tag are required.");
        }

        newProjector.setStatus(ItemStatus.AVAILABLE);
        return projectorGateway.save(newProjector);
    }
}
