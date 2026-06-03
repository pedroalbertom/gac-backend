package com.gac.api.application.service.movement;

import org.springframework.stereotype.Service;

import com.gac.api.domain.model.Movement;
import com.gac.api.domain.model.MovementStatus;
import com.gac.api.domain.model.MovementType;
import com.gac.api.application.repository.MovementRepository;
import java.util.List;

@Service
public class ListActiveLoansService {

    private final MovementRepository movementRepository;

    public ListActiveLoansService(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    public List<Movement> execute() {
        return movementRepository.findByTypeAndStatus(MovementType.LOAN, MovementStatus.OPEN);
    }
}
