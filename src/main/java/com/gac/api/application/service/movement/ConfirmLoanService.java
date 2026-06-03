package com.gac.api.application.service.movement;

import org.springframework.stereotype.Service;

import com.gac.api.application.dto.movement.ConfirmLoanCommand;
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
import com.gac.api.domain.service.movement.ProfessorPendencyRules;
import java.time.LocalDateTime;

@Service
public class ConfirmLoanService {

    private final MovementRepository movementRepository;
    private final ProjectorRepository projectorRepository;
    private final KeyRepository keyRepository;

    public ConfirmLoanService(
            MovementRepository movementRepository, ProjectorRepository projectorRepository, KeyRepository keyRepository) {
        this.movementRepository = movementRepository;
        this.projectorRepository = projectorRepository;
        this.keyRepository = keyRepository;
    }

    public MovementResult execute(ConfirmLoanCommand command) {
        Movement reservation = movementRepository
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
                reservation.getProfessorRegistrationNumber(), movementRepository, null)) {
            throw new BusinessRuleException("Professor has pending issues and cannot borrow (RN05).");
        }

        AssetInventory.requireReservedForProfessor(
                reservation.getAssetType(),
                reservation.getAssetId(),
                reservation.getProfessorRegistrationNumber(),
                projectorRepository,
                keyRepository);

        reservation.setStatus(MovementStatus.COMPLETED);
        movementRepository.save(reservation);

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
                reservation.getAssetType(), reservation.getAssetId(), projectorRepository, keyRepository);

        return MovementResult.from(movementRepository.save(loan));
    }
}
