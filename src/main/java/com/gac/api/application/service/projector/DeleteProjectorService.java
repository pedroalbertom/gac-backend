package com.gac.api.application.service.projector;

import org.springframework.stereotype.Service;

import com.gac.api.domain.model.ItemStatus;
import com.gac.api.domain.model.Projector;
import com.gac.api.domain.exception.BusinessRuleException;
import com.gac.api.domain.exception.NotFoundException;
import com.gac.api.application.repository.ProjectorRepository;

@Service
public class DeleteProjectorService {

    private final ProjectorRepository projectorRepository;

    public DeleteProjectorService(ProjectorRepository projectorRepository) {
        this.projectorRepository = projectorRepository;
    }

    public void execute(Long id) {
        Projector projector = projectorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Projector not found."));

        if (projector.getStatus() == ItemStatus.ON_LOAN || projector.getStatus() == ItemStatus.RESERVED) {
            throw new BusinessRuleException("Cannot delete a projector that is reserved or on loan.");
        }

        projectorRepository.deleteById(id);
    }
}
