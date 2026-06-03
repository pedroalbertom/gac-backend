package com.gac.api.application.service.movement;

import org.springframework.stereotype.Service;

import com.gac.api.domain.service.movement.*;

import com.gac.api.domain.model.Pendency;
import com.gac.api.application.repository.MovementRepository;
import java.util.List;

@Service
public class FindProfessorPendenciesService {

    private final MovementRepository movementRepository;

    public FindProfessorPendenciesService(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    public List<Pendency> execute(String professorRegistrationNumber) {
        return ProfessorPendencyRules.findPendencies(professorRegistrationNumber, movementRepository);
    }
}
