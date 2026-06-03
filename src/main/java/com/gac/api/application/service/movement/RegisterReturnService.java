package com.gac.api.application.service.movement;

import org.springframework.stereotype.Service;

import com.gac.api.application.dto.movement.MovementResult;
import com.gac.api.application.dto.movement.RegisterReturnCommand;
import com.gac.api.domain.exception.BusinessRuleException;
import com.gac.api.domain.exception.NotFoundException;
import com.gac.api.domain.model.Movement;
import com.gac.api.domain.model.MovementStatus;
import com.gac.api.domain.model.MovementType;
import com.gac.api.application.repository.KeyRepository;
import com.gac.api.application.repository.MovementRepository;
import com.gac.api.application.repository.ProjectorRepository;
import com.gac.api.domain.service.movement.AssetInventory;
import com.gac.api.domain.service.movement.LoanAccessoryRules;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegisterReturnService {

    private final MovementRepository movementRepository;
    private final ProjectorRepository projectorRepository;
    private final KeyRepository keyRepository;

    public RegisterReturnService(
            MovementRepository movementRepository, ProjectorRepository projectorRepository, KeyRepository keyRepository) {
        this.movementRepository = movementRepository;
        this.projectorRepository = projectorRepository;
        this.keyRepository = keyRepository;
    }

    public MovementResult execute(RegisterReturnCommand command) {
        Movement loan = movementRepository
                .findById(command.loanId())
                .orElseThrow(() -> new NotFoundException("Loan not found."));

        if (loan.getType() != MovementType.LOAN || loan.getStatus() != MovementStatus.OPEN) {
            throw new BusinessRuleException("Movement is not an open loan.");
        }

        if (command.hasDefect()
                && (command.defectDescription() == null || command.defectDescription().isBlank())) {
            throw new BusinessRuleException("Defect description is required when item has a defect.");
        }

        List<String> confirmedAccessories = LoanAccessoryRules.requireMatchingReturn(
                loan.getAssetType(), loan.getLoanedAccessories(), command.returnedAccessories());

        LocalDateTime now = LocalDateTime.now();
        loan.setStatus(MovementStatus.COMPLETED);
        loan.setReturnedAt(now);
        movementRepository.save(loan);

        Movement returnMovement = new Movement();
        returnMovement.setType(MovementType.RETURN);
        returnMovement.setStatus(MovementStatus.COMPLETED);
        returnMovement.setProfessorRegistrationNumber(loan.getProfessorRegistrationNumber());
        returnMovement.setAttendantId(command.attendantId());
        returnMovement.setAssetType(loan.getAssetType());
        returnMovement.setAssetId(loan.getAssetId());
        returnMovement.setAcademicPurpose(loan.getAcademicPurpose());
        returnMovement.setRoom(loan.getRoom());
        returnMovement.setLoanedAccessories(loan.getLoanedAccessories());
        returnMovement.setReturnedAccessories(confirmedAccessories);
        returnMovement.setReturnedAt(now);
        returnMovement.setCreatedAt(now);

        if (command.hasDefect()) {
            returnMovement.setDefectDescription(command.defectDescription());
            AssetInventory.markMaintenance(
                    loan.getAssetType(), loan.getAssetId(), command.defectDescription(), projectorRepository, keyRepository);
        } else {
            AssetInventory.markAvailable(loan.getAssetType(), loan.getAssetId(), projectorRepository, keyRepository);
        }

        return MovementResult.from(movementRepository.save(returnMovement));
    }
}
