package com.gac.api.core.usecase.movement;

import com.gac.api.core.domain.Movement;
import com.gac.api.core.domain.MovementStatus;
import com.gac.api.core.domain.MovementType;
import com.gac.api.core.gateway.KeyGateway;
import com.gac.api.core.gateway.MovementGateway;
import com.gac.api.core.gateway.ProjectorGateway;
import java.time.LocalDateTime;

public class ExpireReservationsUseCase {

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
