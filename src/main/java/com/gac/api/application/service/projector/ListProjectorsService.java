package com.gac.api.application.service.projector;

import org.springframework.stereotype.Service;

import com.gac.api.domain.model.Projector;
import com.gac.api.application.repository.ProjectorRepository;
import java.util.List;

@Service
public class ListProjectorsService {

    private final ProjectorRepository projectorRepository;

    public ListProjectorsService(ProjectorRepository projectorRepository) {
        this.projectorRepository = projectorRepository;
    }

    public List<Projector> execute() {
        return projectorRepository.findAll();
    }
}
