package com.gac.api.core.usecase.movement;

import com.gac.api.core.domain.Movement;
import com.gac.api.core.domain.MovementStatus;
import com.gac.api.core.domain.MovementType;
import com.gac.api.core.domain.Pendency;
import com.gac.api.core.domain.PendencyType;
import com.gac.api.core.gateway.MovementGateway;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

final class ProfessorPendencyRules {

    private ProfessorPendencyRules() {
    }

    static List<Pendency> findPendencies(String professorRegistrationNumber, MovementGateway movementGateway) {
        LocalDateTime now = LocalDateTime.now();
        List<Pendency> pendencies = new ArrayList<>();

        for (Movement movement : movementGateway.findByProfessorRegistrationNumber(professorRegistrationNumber)) {
            if (movement.getType() == MovementType.LOAN
                    && movement.getStatus() == MovementStatus.OPEN
                    && movement.getCheckedOutAt() != null
                    && now.isAfter(ShiftRules.loanReturnDeadline(movement.getCheckedOutAt()))) {
                pendencies.add(new Pendency(
                        PendencyType.OVERDUE_LOAN,
                        movement.getId(),
                        "Open loan past the return deadline for its shift (RN04)."));
            }

            if (movement.getType() == MovementType.RESERVATION
                    && movement.getStatus() == MovementStatus.OPEN
                    && movement.getCreatedAt() != null
                    && now.isAfter(ShiftRules.reservationExpiry(movement.getCreatedAt()))) {
                pendencies.add(new Pendency(
                        PendencyType.EXPIRED_RESERVATION,
                        movement.getId(),
                        "Reservation expired without pickup (RN11)."));
            }
        }

        return pendencies;
    }

    static boolean hasBlockingPendency(
            String professorRegistrationNumber, MovementGateway movementGateway, Long excludeMovementId) {
        return findPendencies(professorRegistrationNumber, movementGateway).stream()
                .filter(p -> excludeMovementId == null || !excludeMovementId.equals(p.getMovementId()))
                .findAny()
                .isPresent();
    }
}
