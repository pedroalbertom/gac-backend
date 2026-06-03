package com.gac.api.application.usecase.movement;

import com.gac.api.application.port.in.movement.ExpireReservationsInputPort;

import com.gac.api.domain.service.movement.*;

import com.gac.api.domain.model.Movement;
import com.gac.api.domain.model.MovementStatus;
import com.gac.api.domain.model.MovementType;
import com.gac.api.application.port.out.KeyGateway;
import com.gac.api.application.port.out.MovementGateway;
import com.gac.api.application.port.out.ProjectorGateway;
import java.time.LocalDateTime;

public class ExpireReservationsUseCase implements ExpireReservationsInputPort {

    private final MovementGateway movementGateway;
    private final ProjectorGateway projectorGateway;
    private final KeyGateway keyGateway;

    public ExpireReservationsUseCase(
            MovementGateway movementGateway, ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        this.movementGateway = movementGateway;
        this.projectorGateway = projectorGateway;
        this.keyGateway = keyGateway;
    }

    public int execute() {
        LocalDateTime now = LocalDateTime.now();
        int expiredCount = 0;

        for (Movement reservation :
                movementGateway.findByTypeAndStatus(MovementType.RESERVATION, MovementStatus.OPEN)) {
            if (reservation.getCreatedAt() == null || !now.isAfter(ShiftRules.reservationExpiry(reservation.getCreatedAt()))) {
                continue;
            }

            reservation.setStatus(MovementStatus.CANCELLED);
            movementGateway.save(reservation);
            AssetInventory.markAvailable(
                    reservation.getAssetType(), reservation.getAssetId(), projectorGateway, keyGateway);
            expiredCount++;
        }

        return expiredCount;
    }
}
