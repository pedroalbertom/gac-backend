package com.gac.api.application.usecase.movement;

import com.gac.api.application.dto.movement.ConfirmLoanCommand;
import com.gac.api.application.dto.movement.MovementResult;
import com.gac.api.application.port.in.movement.ConfirmLoanInputPort;
import com.gac.api.domain.exception.BusinessRuleException;
import com.gac.api.domain.exception.NotFoundException;
import com.gac.api.domain.model.Movement;
import com.gac.api.domain.model.MovementStatus;
import com.gac.api.domain.model.MovementType;
import com.gac.api.domain.port.KeyGateway;
import com.gac.api.domain.port.MovementGateway;
import com.gac.api.domain.port.ProjectorGateway;
import com.gac.api.domain.service.movement.AssetInventory;
import com.gac.api.domain.service.movement.ProfessorPendencyRules;
import java.time.LocalDateTime;

public class ConfirmLoanUseCase implements ConfirmLoanInputPort {

    private final MovementGateway movementGateway;
    private final ProjectorGateway projectorGateway;
    private final KeyGateway keyGateway;

    public ConfirmLoanUseCase(
            MovementGateway movementGateway, ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        this.movementGateway = movementGateway;
        this.projectorGateway = projectorGateway;
        this.keyGateway = keyGateway;
    }

    @Override
    public MovementResult execute(ConfirmLoanCommand command) {
        Movement reservation = movementGateway
                .findById(command.reservationId())
                .orElseThrow(() -> new NotFoundException("Reservation not found."));

        if (reservation.getType() != MovementType.RESERVATION || reservation.getStatus() != MovementStatus.OPEN) {
            throw new BusinessRuleException("Movement is not an open reservation.");
        }

        if (command.confirmationCode() == null
                || !command.confirmationCode().equals(reservation.getConfirmationCode())) {
            throw new BusinessRuleException("Invalid confirmation code.");
        }

        if (ProfessorPendencyRules.hasBlockingPendency(
                reservation.getProfessorRegistrationNumber(), movementGateway, null)) {
            throw new BusinessRuleException("Professor has pending issues and cannot borrow (RN05).");
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
        loan.setAttendantId(command.attendantId());
        loan.setAssetType(reservation.getAssetType());
        loan.setAssetId(reservation.getAssetId());
        loan.setAcademicPurpose(reservation.getAcademicPurpose());
        loan.setRoom(command.room());
        loan.setLoanedAccessories(command.loanedAccessories());
        loan.setCheckedOutAt(LocalDateTime.now());
        loan.setCreatedAt(LocalDateTime.now());

        AssetInventory.markOnLoan(
                reservation.getAssetType(), reservation.getAssetId(), projectorGateway, keyGateway);

        return MovementResult.from(movementGateway.save(loan));
    }
}
