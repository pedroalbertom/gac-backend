package com.gac.api.core.usecase.movement;

import com.gac.api.core.domain.Movement;
import com.gac.api.core.domain.MovementStatus;
import com.gac.api.core.domain.MovementType;
import com.gac.api.core.domain.User;
import com.gac.api.core.gateway.KeyGateway;
import com.gac.api.core.gateway.MovementGateway;
import com.gac.api.core.gateway.ProjectorGateway;
import java.time.LocalDateTime;

public class RegisterReturnUseCase {

    private final MovementGateway movementGateway;
    private final ProjectorGateway projectorGateway;
    private final KeyGateway keyGateway;

    public RegisterReturnUseCase(
            MovementGateway movementGateway, ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        this.movementGateway = movementGateway;
        this.projectorGateway = projectorGateway;
        this.keyGateway = keyGateway;
    }

    public Movement execute(Long loanId, User attendant, boolean hasDefect, String defectDescription) {
        Movement loan = movementGateway
                .findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found."));

        if (loan.getType() != MovementType.LOAN || loan.getStatus() != MovementStatus.OPEN) {
            throw new RuntimeException("Movement is not an open loan.");
        }

        if (hasDefect && (defectDescription == null || defectDescription.isBlank())) {
            throw new RuntimeException("Defect description is required when item has a defect.");
        }

        LocalDateTime now = LocalDateTime.now();
        loan.setStatus(MovementStatus.COMPLETED);
        loan.setReturnedAt(now);
        movementGateway.save(loan);

        Movement returnMovement = new Movement();
        returnMovement.setType(MovementType.RETURN);
        returnMovement.setStatus(MovementStatus.COMPLETED);
        returnMovement.setProfessorRegistrationNumber(loan.getProfessorRegistrationNumber());
        returnMovement.setAttendantId(attendant.getId());
        returnMovement.setAssetType(loan.getAssetType());
        returnMovement.setAssetId(loan.getAssetId());
        returnMovement.setAcademicPurpose(loan.getAcademicPurpose());
        returnMovement.setRoom(loan.getRoom());
        returnMovement.setLoanedAccessories(loan.getLoanedAccessories());
        returnMovement.setReturnedAt(now);
        returnMovement.setCreatedAt(now);

        if (hasDefect) {
            returnMovement.setDefectDescription(defectDescription);
            AssetInventory.markMaintenance(
                    loan.getAssetType(), loan.getAssetId(), defectDescription, projectorGateway, keyGateway);
        } else {
            AssetInventory.markAvailable(loan.getAssetType(), loan.getAssetId(), projectorGateway, keyGateway);
        }

        return movementGateway.save(returnMovement);
    }
}
