package com.gac.api.application.service.projector;

import org.springframework.stereotype.Service;

import com.gac.api.domain.model.Projector;
import com.gac.api.domain.exception.NotFoundException;
import com.gac.api.application.repository.ProjectorRepository;

@Service
public class GetProjectorByIdService {

    private final ProjectorRepository projectorRepository;

    public GetProjectorByIdService(ProjectorRepository projectorRepository) {
        this.projectorRepository = projectorRepository;
    }

    public Projector execute(Long id) {
        return projectorRepository.findById(id).orElseThrow(() -> new NotFoundException("Projector not found."));
    }
}
