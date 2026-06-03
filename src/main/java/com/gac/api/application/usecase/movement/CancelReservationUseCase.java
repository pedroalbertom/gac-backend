package com.gac.api.application.usecase.movement;

import com.gac.api.application.port.in.movement.CancelReservationInputPort;

import com.gac.api.domain.service.movement.*;

import com.gac.api.domain.model.Movement;
import com.gac.api.domain.model.MovementStatus;
import com.gac.api.domain.model.MovementType;
import com.gac.api.application.port.out.KeyGateway;
import com.gac.api.application.port.out.MovementGateway;
import com.gac.api.application.port.out.ProjectorGateway;

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

    public Movement execute(Long reservationId, String professorRegistrationNumber) {
        Movement reservation = movementGateway
                .findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found."));

        if (reservation.getType() != MovementType.RESERVATION || reservation.getStatus() != MovementStatus.OPEN) {
            throw new RuntimeException("Movement is not an open reservation.");
        }

        if (!professorRegistrationNumber.equals(reservation.getProfessorRegistrationNumber())) {
            throw new RuntimeException("Reservation does not belong to this professor.");
        }

        reservation.setStatus(MovementStatus.CANCELLED);
        movementGateway.save(reservation);

        AssetInventory.markAvailable(
                reservation.getAssetType(), reservation.getAssetId(), projectorGateway, keyGateway);

        return reservation;
    }
}
