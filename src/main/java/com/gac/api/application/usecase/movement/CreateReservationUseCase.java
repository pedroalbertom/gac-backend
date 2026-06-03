package com.gac.api.application.usecase.movement;

import com.gac.api.application.port.in.movement.CreateReservationInputPort;

import com.gac.api.domain.service.movement.*;

import com.gac.api.domain.model.AssetType;
import com.gac.api.domain.model.Movement;
import com.gac.api.domain.model.MovementStatus;
import com.gac.api.domain.model.MovementType;
import com.gac.api.application.port.out.KeyGateway;
import com.gac.api.application.port.out.MovementGateway;
import com.gac.api.application.port.out.ProjectorGateway;
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

    public Movement execute(
            String professorRegistrationNumber, AssetType assetType, Long assetId, String academicPurpose) {
        if (ProfessorPendencyRules.hasBlockingPendency(professorRegistrationNumber, movementGateway, null)) {
            throw new RuntimeException("Professor has pending issues and cannot reserve (RN05).");
        }

        if (movementGateway.countActiveByProfessorAndAssetType(professorRegistrationNumber, assetType) >= 1) {
            throw new RuntimeException("Professor already has an active reservation or loan for this asset type.");
        }

        if (movementGateway.findOpenByAsset(assetType, assetId, MovementType.RESERVATION).isPresent()) {
            throw new RuntimeException("Asset already has an open reservation.");
        }

        AssetInventory.requireAvailable(assetType, assetId, projectorGateway, keyGateway);

        Movement reservation = new Movement();
        reservation.setType(MovementType.RESERVATION);
        reservation.setStatus(MovementStatus.OPEN);
        reservation.setProfessorRegistrationNumber(professorRegistrationNumber);
        reservation.setAssetType(assetType);
        reservation.setAssetId(assetId);
        reservation.setAcademicPurpose(academicPurpose);
        reservation.setConfirmationCode(ConfirmationCodeGenerator.generate());
        reservation.setCreatedAt(LocalDateTime.now());

        AssetInventory.markReserved(assetType, assetId, professorRegistrationNumber, projectorGateway, keyGateway);

        return movementGateway.save(reservation);
    }
}
