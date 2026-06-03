package com.gac.api.application.service.movement;

import org.springframework.stereotype.Service;

import com.gac.api.application.dto.movement.CancelReservationCommand;
import com.gac.api.application.dto.movement.MovementResult;
import com.gac.api.domain.exception.BusinessRuleException;
import com.gac.api.domain.exception.NotFoundException;
import com.gac.api.domain.model.Movement;
import com.gac.api.domain.model.MovementStatus;
import com.gac.api.domain.model.MovementType;
import com.gac.api.application.repository.KeyRepository;
import com.gac.api.application.repository.MovementRepository;
import com.gac.api.application.repository.ProjectorRepository;
import com.gac.api.domain.service.movement.AssetInventory;

@Service
public class CancelReservationService {

    private final MovementRepository movementRepository;
    private final ProjectorRepository projectorRepository;
    private final KeyRepository keyRepository;

    public CancelReservationService(
            MovementRepository movementRepository, ProjectorRepository projectorRepository, KeyRepository keyRepository) {
        this.movementRepository = movementRepository;
        this.projectorRepository = projectorRepository;
        this.keyRepository = keyRepository;
    }

    public MovementResult execute(CancelReservationCommand command) {
        Movement reservation = movementRepository
                .findById(command.reservationId())
                .orElseThrow(() -> new NotFoundException("Reservation not found."));

        if (reservation.getType() != MovementType.RESERVATION || reservation.getStatus() != MovementStatus.OPEN) {
            throw new BusinessRuleException("Movement is not an open reservation.");
        }

        if (!command.professorRegistrationNumber().equals(reservation.getProfessorRegistrationNumber())) {
            throw new BusinessRuleException("Reservation does not belong to this professor.");
        }

        reservation.setStatus(MovementStatus.CANCELLED);
        movementRepository.save(reservation);

        AssetInventory.markAvailable(
                reservation.getAssetType(), reservation.getAssetId(), projectorRepository, keyRepository);

        return MovementResult.from(reservation);
    }
}
