package com.gac.api.application.usecase.movement;

import com.gac.api.application.dto.movement.MovementResult;
import com.gac.api.application.dto.movement.RegisterReturnCommand;
import com.gac.api.application.port.in.movement.RegisterReturnInputPort;
import com.gac.api.domain.exception.BusinessRuleException;
import com.gac.api.domain.exception.NotFoundException;
import com.gac.api.domain.model.Movement;
import com.gac.api.domain.model.MovementStatus;
import com.gac.api.domain.model.MovementType;
import com.gac.api.domain.port.KeyGateway;
import com.gac.api.domain.port.MovementGateway;
import com.gac.api.domain.port.ProjectorGateway;
import com.gac.api.domain.service.movement.AssetInventory;
import com.gac.api.domain.service.movement.LoanAccessoryRules;
import java.time.LocalDateTime;
import java.util.List;

public class RegisterReturnUseCase implements RegisterReturnInputPort {

    private final MovementGateway movementGateway;
    private final ProjectorGateway projectorGateway;
    private final KeyGateway keyGateway;

    public RegisterReturnUseCase(
            MovementGateway movementGateway, ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        this.movementGateway = movementGateway;
        this.projectorGateway = projectorGateway;
        this.keyGateway = keyGateway;
    }

    @Override
    public MovementResult execute(RegisterReturnCommand command) {
        Movement loan = movementGateway
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
        movementGateway.save(loan);

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
                    loan.getAssetType(), loan.getAssetId(), command.defectDescription(), projectorGateway, keyGateway);
        } else {
            AssetInventory.markAvailable(loan.getAssetType(), loan.getAssetId(), projectorGateway, keyGateway);
        }

        return MovementResult.from(movementGateway.save(returnMovement));
    }
}
