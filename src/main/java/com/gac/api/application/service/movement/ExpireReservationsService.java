package com.gac.api.application.service.movement;

import org.springframework.stereotype.Service;

import com.gac.api.domain.service.movement.*;

import com.gac.api.domain.model.Movement;
import com.gac.api.domain.model.MovementStatus;
import com.gac.api.domain.model.MovementType;
import com.gac.api.application.repository.KeyRepository;
import com.gac.api.application.repository.MovementRepository;
import com.gac.api.application.repository.ProjectorRepository;
import java.time.LocalDateTime;

@Service
public class ExpireReservationsService {

    private final MovementRepository movementRepository;
    private final ProjectorRepository projectorRepository;
    private final KeyRepository keyRepository;

    public ExpireReservationsService(
            MovementRepository movementRepository, ProjectorRepository projectorRepository, KeyRepository keyRepository) {
        this.movementRepository = movementRepository;
        this.projectorRepository = projectorRepository;
        this.keyRepository = keyRepository;
    }

    public int execute() {
        LocalDateTime now = LocalDateTime.now();
        int expiredCount = 0;

        for (Movement reservation :
                movementRepository.findByTypeAndStatus(MovementType.RESERVATION, MovementStatus.OPEN)) {
            if (reservation.getCreatedAt() == null || !now.isAfter(ShiftRules.reservationExpiry(reservation.getCreatedAt()))) {
                continue;
            }

            reservation.setStatus(MovementStatus.CANCELLED);
            movementRepository.save(reservation);
            AssetInventory.markAvailable(
                    reservation.getAssetType(), reservation.getAssetId(), projectorRepository, keyRepository);
            expiredCount++;
        }

        return expiredCount;
    }
}
