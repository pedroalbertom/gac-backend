package com.gac.api.application.service.movement;

import org.springframework.stereotype.Service;

import com.gac.api.domain.model.Movement;
import com.gac.api.application.repository.MovementRepository;
import java.util.List;

@Service
public class FindMovementsByProfessorService {

    private final MovementRepository movementRepository;

    public FindMovementsByProfessorService(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    public List<Movement> execute(String registrationNumber) {
        return movementRepository.findByProfessorRegistrationNumber(registrationNumber);
    }
}
