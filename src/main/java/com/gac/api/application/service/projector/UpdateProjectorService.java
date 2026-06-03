package com.gac.api.application.service.projector;

import org.springframework.stereotype.Service;

import com.gac.api.domain.model.Projector;
import com.gac.api.domain.exception.NotFoundException;
import com.gac.api.application.repository.ProjectorRepository;

@Service
public class UpdateProjectorService {

    private final ProjectorRepository projectorRepository;

    public UpdateProjectorService(ProjectorRepository projectorRepository) {
        this.projectorRepository = projectorRepository;
    }

    public Projector execute(Long id, Projector updatedData) {
        Projector existing = projectorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Projector not found."));

        existing.setBrand(updatedData.getBrand());
        existing.setModel(updatedData.getModel());
        existing.setAssetTag(updatedData.getAssetTag());
        existing.setSerialNumber(updatedData.getSerialNumber());

        return projectorRepository.save(existing);
    }
}
