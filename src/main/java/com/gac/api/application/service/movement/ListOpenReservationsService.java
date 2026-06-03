package com.gac.api.application.service.movement;

import org.springframework.stereotype.Service;

import com.gac.api.domain.model.Movement;
import com.gac.api.domain.model.MovementType;
import com.gac.api.application.repository.MovementRepository;
import java.util.List;

@Service
public class ListOpenReservationsService {

    private final MovementRepository movementRepository;

    public ListOpenReservationsService(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    public List<Movement> execute(String professorRegistrationNumber) {
        return movementRepository.findOpenByProfessorAndType(professorRegistrationNumber, MovementType.RESERVATION);
    }
}
