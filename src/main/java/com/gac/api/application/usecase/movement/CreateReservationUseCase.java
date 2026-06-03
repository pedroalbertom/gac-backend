package com.gac.api.application.usecase.movement;

import com.gac.api.application.dto.movement.CreateReservationCommand;
import com.gac.api.application.dto.movement.MovementResult;
import com.gac.api.application.port.in.movement.CreateReservationInputPort;
import com.gac.api.domain.exception.BusinessRuleException;
import com.gac.api.domain.model.Movement;
import com.gac.api.domain.model.MovementStatus;
import com.gac.api.domain.model.MovementType;
import com.gac.api.domain.port.KeyGateway;
import com.gac.api.domain.port.MovementGateway;
import com.gac.api.domain.port.ProjectorGateway;
import com.gac.api.domain.service.movement.AssetInventory;
import com.gac.api.domain.service.movement.ConfirmationCodeGenerator;
import com.gac.api.domain.service.movement.ProfessorPendencyRules;
import java.time.LocalDateTime;

public class CreateReservationUseCase implements CreateReservationInputPort {

    private final MovementGateway movementGateway;
    private final ProjectorGateway projectorGateway;
    private final KeyGateway keyGateway;

    public CreateReservationUseCase(
            MovementGateway movementGateway, ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        this.movementGateway = movementGateway;
        this.projectorGateway = projectorGateway;
        this.keyGateway = keyGateway;
    }

    @Override
    public MovementResult execute(CreateReservationCommand command) {
        if (ProfessorPendencyRules.hasBlockingPendency(
                command.professorRegistrationNumber(), movementGateway, null)) {
            throw new BusinessRuleException("Professor has pending issues and cannot reserve (RN05).");
        }

        if (movementGateway.countActiveByProfessorAndAssetType(
                        command.professorRegistrationNumber(), command.assetType())
                >= 1) {
            throw new BusinessRuleException(
                    "Professor already has an active reservation or loan for this asset type.");
        }

        if (movementGateway
                .findOpenByAsset(command.assetType(), command.assetId(), MovementType.RESERVATION)
                .isPresent()) {
            throw new BusinessRuleException("Asset already has an open reservation.");
        }

        AssetInventory.requireAvailable(
                command.assetType(), command.assetId(), projectorGateway, keyGateway);

        Movement reservation = new Movement();
        reservation.setType(MovementType.RESERVATION);
        reservation.setStatus(MovementStatus.OPEN);
        reservation.setProfessorRegistrationNumber(command.professorRegistrationNumber());
        reservation.setAssetType(command.assetType());
        reservation.setAssetId(command.assetId());
        reservation.setAcademicPurpose(command.academicPurpose());
        reservation.setConfirmationCode(ConfirmationCodeGenerator.generate());
        reservation.setCreatedAt(LocalDateTime.now());

        AssetInventory.markReserved(
                command.assetType(),
                command.assetId(),
                command.professorRegistrationNumber(),
                projectorGateway,
                keyGateway);

        return MovementResult.from(movementGateway.save(reservation));
    }
}
