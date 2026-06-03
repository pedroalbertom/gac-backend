package com.gac.api.domain.service.movement;

import com.gac.api.domain.model.Movement;
import com.gac.api.domain.model.MovementStatus;
import com.gac.api.domain.model.MovementType;
import com.gac.api.domain.model.Pendency;
import com.gac.api.domain.model.PendencyType;
import com.gac.api.application.repository.MovementRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class ProfessorPendencyRules {

    private ProfessorPendencyRules() {
    }

    public static List<Pendency> findPendencies(String professorRegistrationNumber, MovementRepository movementGateway) {
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

    public static boolean hasBlockingPendency(
            String professorRegistrationNumber, MovementRepository movementGateway, Long excludeMovementId) {
        return findPendencies(professorRegistrationNumber, movementGateway).stream()
                .filter(p -> excludeMovementId == null || !excludeMovementId.equals(p.getMovementId()))
                .findAny()
                .isPresent();
    }
}
