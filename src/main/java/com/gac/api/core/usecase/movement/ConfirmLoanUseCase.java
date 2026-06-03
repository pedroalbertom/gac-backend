package com.gac.api.core.usecase.movement;

import com.gac.api.core.domain.Movement;
import com.gac.api.core.domain.MovementStatus;
import com.gac.api.core.domain.MovementType;
import com.gac.api.core.domain.User;
import com.gac.api.core.gateway.KeyGateway;
import com.gac.api.core.gateway.MovementGateway;
import com.gac.api.core.gateway.ProjectorGateway;
import java.time.LocalDateTime;
import java.util.List;

public class ConfirmLoanUseCase {

    private final MovementGateway movementGateway;
    private final ProjectorGateway projectorGateway;
    private final KeyGateway keyGateway;

    public ConfirmLoanUseCase(
            MovementGateway movementGateway, ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        this.movementGateway = movementGateway;
        this.projectorGateway = projectorGateway;
        this.keyGateway = keyGateway;
    }

    public Movement execute(
            Long reservationId,
            String confirmationCode,
            User attendant,
            String room,
            List<String> loanedAccessories) {
        Movement reservation = movementGateway
                .findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found."));

        if (reservation.getType() != MovementType.RESERVATION || reservation.getStatus() != MovementStatus.OPEN) {
            throw new RuntimeException("Movement is not an open reservation.");
        }

        if (confirmationCode == null || !confirmationCode.equals(reservation.getConfirmationCode())) {
            throw new RuntimeException("Invalid confirmation code.");
        }

        AssetInventory.requireReservedForProfessor(
                reservation.getAssetType(),
                reservation.getAssetId(),
                reservation.getProfessorRegistrationNumber(),
                projectorGateway,
                keyGateway);

        reservation.setStatus(MovementStatus.COMPLETED);
        movementGateway.save(reservation);

        Movement loan = new Movement();
        loan.setType(MovementType.LOAN);
        loan.setStatus(MovementStatus.OPEN);
        loan.setProfessorRegistrationNumber(reservation.getProfessorRegistrationNumber());
        loan.setAttendantId(attendant.getId());
        loan.setAssetType(reservation.getAssetType());
        loan.setAssetId(reservation.getAssetId());
        loan.setAcademicPurpose(reservation.getAcademicPurpose());
        loan.setRoom(room);
        loan.setLoanedAccessories(loanedAccessories);
        loan.setCheckedOutAt(LocalDateTime.now());
        loan.setCreatedAt(LocalDateTime.now());

        AssetInventory.markOnLoan(
                reservation.getAssetType(), reservation.getAssetId(), projectorGateway, keyGateway);

        return movementGateway.save(loan);
    }
}
