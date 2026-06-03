package com.gac.api.application.service.projector;

import org.springframework.stereotype.Service;

import com.gac.api.domain.model.ItemStatus;
import com.gac.api.domain.model.Projector;
import com.gac.api.domain.exception.BusinessRuleException;
import com.gac.api.domain.exception.ConflictException;
import com.gac.api.application.repository.ProjectorRepository;

@Service
public class CreateProjectorService {

    private final ProjectorRepository projectorRepository;

    public CreateProjectorService(ProjectorRepository projectorRepository) {
        this.projectorRepository = projectorRepository;
    }

    public Projector execute(Projector newProjector) {
        projectorRepository.findByAssetTag(newProjector.getAssetTag())
                .ifPresent(p -> {
                    throw new ConflictException("A projector with this asset tag already exists.");
                });

        if (newProjector.getBrand() == null || newProjector.getAssetTag() == null) {
            throw new BusinessRuleException("Brand and asset tag are required.");
        }

        newProjector.setStatus(ItemStatus.AVAILABLE);
        return projectorRepository.save(newProjector);
    }
}
