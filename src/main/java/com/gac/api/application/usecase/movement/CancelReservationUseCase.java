package com.gac.api.application.usecase.movement;

import com.gac.api.application.dto.movement.CancelReservationCommand;
import com.gac.api.application.dto.movement.MovementResult;
import com.gac.api.application.port.in.movement.CancelReservationInputPort;
import com.gac.api.domain.exception.BusinessRuleException;
import com.gac.api.domain.exception.NotFoundException;
import com.gac.api.domain.model.Movement;
import com.gac.api.domain.model.MovementStatus;
import com.gac.api.domain.model.MovementType;
import com.gac.api.domain.port.KeyGateway;
import com.gac.api.domain.port.MovementGateway;
import com.gac.api.domain.port.ProjectorGateway;
import com.gac.api.domain.service.movement.AssetInventory;

public class CancelReservationUseCase implements CancelReservationInputPort {

    private final MovementGateway movementGateway;
    private final ProjectorGateway projectorGateway;
    private final KeyGateway keyGateway;

    public CancelReservationUseCase(
            MovementGateway movementGateway, ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        this.movementGateway = movementGateway;
        this.projectorGateway = projectorGateway;
        this.keyGateway = keyGateway;
    }

    @Override
    public MovementResult execute(CancelReservationCommand command) {
        Movement reservation = movementGateway
                .findById(command.reservationId())
                .orElseThrow(() -> new NotFoundException("Reservation not found."));

        if (reservation.getType() != MovementType.RESERVATION || reservation.getStatus() != MovementStatus.OPEN) {
            throw new BusinessRuleException("Movement is not an open reservation.");
        }

        if (!command.professorRegistrationNumber().equals(reservation.getProfessorRegistrationNumber())) {
            throw new BusinessRuleException("Reservation does not belong to this professor.");
        }

        reservation.setStatus(MovementStatus.CANCELLED);
        movementGateway.save(reservation);

        AssetInventory.markAvailable(
                reservation.getAssetType(), reservation.getAssetId(), projectorGateway, keyGateway);

        return MovementResult.from(reservation);
    }
}
