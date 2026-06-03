package com.gac.api.application.usecase.movement;

import com.gac.api.application.port.in.movement.ExchangeAssetInputPort;

import com.gac.api.domain.service.movement.*;

import com.gac.api.domain.model.AssetType;
import com.gac.api.domain.model.Movement;
import com.gac.api.domain.model.MovementStatus;
import com.gac.api.domain.model.MovementType;
import com.gac.api.domain.exception.BusinessRuleException;
import com.gac.api.domain.exception.NotFoundException;
import com.gac.api.domain.model.User;
import com.gac.api.domain.port.KeyGateway;
import com.gac.api.domain.port.MovementGateway;
import com.gac.api.domain.port.ProjectorGateway;
import java.time.LocalDateTime;
import java.util.List;

public class ExchangeAssetUseCase implements ExchangeAssetInputPort {

    private final MovementGateway movementGateway;
    private final ProjectorGateway projectorGateway;
    private final KeyGateway keyGateway;

    public ExchangeAssetUseCase(
            MovementGateway movementGateway, ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        this.movementGateway = movementGateway;
        this.projectorGateway = projectorGateway;
        this.keyGateway = keyGateway;
    }

    public Movement execute(
            Long loanId,
            AssetType substituteAssetType,
            Long substituteAssetId,
            String defectDescription,
            User attendant,
            String room,
            List<String> loanedAccessories) {
        if (defectDescription == null || defectDescription.isBlank()) {
            throw new BusinessRuleException("Defect description is required for exchange.");
        }

        Movement loan = movementGateway
                .findById(loanId)
                .orElseThrow(() -> new NotFoundException("Loan not found."));

        if (loan.getType() != MovementType.LOAN || loan.getStatus() != MovementStatus.OPEN) {
            throw new BusinessRuleException("Movement is not an open loan.");
        }

        if (loan.getAssetType() != substituteAssetType) {
            throw new BusinessRuleException("Substitute asset must be the same type as the loaned asset.");
        }

        if (loan.getAssetId().equals(substituteAssetId)) {
            throw new BusinessRuleException("Substitute asset must be different from the defective asset.");
        }

        if (ProfessorPendencyRules.hasBlockingPendency(
                loan.getProfessorRegistrationNumber(), movementGateway, loanId)) {
            throw new BusinessRuleException("Professor has pending issues that block this operation (RN05).");
        }

        AssetInventory.requireAvailable(substituteAssetType, substituteAssetId, projectorGateway, keyGateway);

        LocalDateTime now = LocalDateTime.now();
        loan.setStatus(MovementStatus.COMPLETED);
        loan.setReturnedAt(now);
        movementGateway.save(loan);

        Movement exchange = new Movement();
        exchange.setType(MovementType.EXCHANGE);
        exchange.setStatus(MovementStatus.COMPLETED);
        exchange.setProfessorRegistrationNumber(loan.getProfessorRegistrationNumber());
        exchange.setAttendantId(attendant.getId());
        exchange.setAssetType(loan.getAssetType());
        exchange.setAssetId(loan.getAssetId());
        exchange.setAcademicPurpose(loan.getAcademicPurpose());
        exchange.setRoom(room != null ? room : loan.getRoom());
        exchange.setDefectDescription(defectDescription);
        exchange.setLoanedAccessories(loan.getLoanedAccessories());
        exchange.setCheckedOutAt(loan.getCheckedOutAt());
        exchange.setReturnedAt(now);
        exchange.setCreatedAt(now);
        movementGateway.save(exchange);

        AssetInventory.markMaintenance(
                loan.getAssetType(), loan.getAssetId(), defectDescription, projectorGateway, keyGateway);

        Movement newLoan = new Movement();
        newLoan.setType(MovementType.LOAN);
        newLoan.setStatus(MovementStatus.OPEN);
        newLoan.setProfessorRegistrationNumber(loan.getProfessorRegistrationNumber());
        newLoan.setAttendantId(attendant.getId());
        newLoan.setAssetType(substituteAssetType);
        newLoan.setAssetId(substituteAssetId);
        newLoan.setAcademicPurpose(loan.getAcademicPurpose());
        newLoan.setRoom(room != null ? room : loan.getRoom());
        newLoan.setLoanedAccessories(loanedAccessories);
        newLoan.setCheckedOutAt(now);
        newLoan.setCreatedAt(now);

        AssetInventory.markOnLoan(substituteAssetType, substituteAssetId, projectorGateway, keyGateway);

        return movementGateway.save(newLoan);
    }
}
