package com.gac.api.core.usecase.projector;

import com.gac.api.core.domain.ItemStatus;
import com.gac.api.core.domain.Projector;
import com.gac.api.core.exception.BusinessRuleException;
import com.gac.api.core.exception.ConflictException;
import com.gac.api.core.gateway.ProjectorGateway;

public class CreateProjectorUseCase {

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
