package com.gac.api.application.service.movement;

import org.springframework.stereotype.Service;

import com.gac.api.application.dto.movement.ExchangeAssetCommand;
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
public class ExchangeAssetService {

    private final MovementRepository movementRepository;
    private final ProjectorRepository projectorRepository;
    private final KeyRepository keyRepository;

    public ExchangeAssetService(
            MovementRepository movementRepository, ProjectorRepository projectorRepository, KeyRepository keyRepository) {
        this.movementRepository = movementRepository;
        this.projectorRepository = projectorRepository;
        this.keyRepository = keyRepository;
    }

    public MovementResult execute(ExchangeAssetCommand command) {
        if (command.defectDescription() == null || command.defectDescription().isBlank()) {
            throw new BusinessRuleException("Defect description is required for exchange.");
        }

        Movement loan = movementRepository
                .findById(command.loanId())
                .orElseThrow(() -> new NotFoundException("Loan not found."));

        if (loan.getType() != MovementType.LOAN || loan.getStatus() != MovementStatus.OPEN) {
            throw new BusinessRuleException("Movement is not an open loan.");
        }

        if (loan.getAssetType() != command.substituteAssetType()) {
            throw new BusinessRuleException("Substitute asset must be the same type as the loaned asset.");
        }

        if (loan.getAssetId().equals(command.substituteAssetId())) {
            throw new BusinessRuleException("Substitute asset must be different from the defective asset.");
        }

        if (ProfessorPendencyRules.hasBlockingPendency(
                loan.getProfessorRegistrationNumber(), movementRepository, command.loanId())) {
            throw new BusinessRuleException("Professor has pending issues that block this operation (RN05).");
        }

        AssetInventory.requireAvailable(
                command.substituteAssetType(), command.substituteAssetId(), projectorRepository, keyRepository);

        LocalDateTime now = LocalDateTime.now();
        loan.setStatus(MovementStatus.COMPLETED);
        loan.setReturnedAt(now);
        movementRepository.save(loan);

        Movement exchange = new Movement();
        exchange.setType(MovementType.EXCHANGE);
        exchange.setStatus(MovementStatus.COMPLETED);
        exchange.setProfessorRegistrationNumber(loan.getProfessorRegistrationNumber());
        exchange.setAttendantId(command.attendantId());
        exchange.setAssetType(loan.getAssetType());
        exchange.setAssetId(loan.getAssetId());
        exchange.setAcademicPurpose(loan.getAcademicPurpose());
        exchange.setRoom(command.room() != null ? command.room() : loan.getRoom());
        exchange.setDefectDescription(command.defectDescription());
        exchange.setLoanedAccessories(loan.getLoanedAccessories());
        exchange.setCheckedOutAt(loan.getCheckedOutAt());
        exchange.setReturnedAt(now);
        exchange.setCreatedAt(now);
        movementRepository.save(exchange);

        AssetInventory.markMaintenance(
                loan.getAssetType(), loan.getAssetId(), command.defectDescription(), projectorRepository, keyRepository);

        Movement newLoan = new Movement();
        newLoan.setType(MovementType.LOAN);
        newLoan.setStatus(MovementStatus.OPEN);
        newLoan.setProfessorRegistrationNumber(loan.getProfessorRegistrationNumber());
        newLoan.setAttendantId(command.attendantId());
        newLoan.setAssetType(command.substituteAssetType());
        newLoan.setAssetId(command.substituteAssetId());
        newLoan.setAcademicPurpose(loan.getAcademicPurpose());
        newLoan.setRoom(command.room() != null ? command.room() : loan.getRoom());
        newLoan.setLoanedAccessories(command.loanedAccessories());
        newLoan.setCheckedOutAt(now);
        newLoan.setCreatedAt(now);

        AssetInventory.markOnLoan(
                command.substituteAssetType(), command.substituteAssetId(), projectorRepository, keyRepository);

        return MovementResult.from(movementRepository.save(newLoan));
    }
}
