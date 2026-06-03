package com.gac.api.application.service.movement;

import org.springframework.stereotype.Service;

import com.gac.api.application.dto.movement.CreateReservationCommand;
import com.gac.api.application.dto.movement.MovementResult;
import com.gac.api.domain.exception.BusinessRuleException;
import com.gac.api.domain.model.Movement;
import com.gac.api.domain.model.MovementStatus;
import com.gac.api.domain.model.MovementType;
import com.gac.api.application.repository.KeyRepository;
import com.gac.api.application.repository.MovementRepository;
import com.gac.api.application.repository.ProjectorRepository;
import com.gac.api.domain.service.movement.AssetInventory;
import com.gac.api.domain.service.movement.ConfirmationCodeGenerator;
import com.gac.api.domain.service.movement.ProfessorPendencyRules;
import java.time.LocalDateTime;

@Service
public class CreateReservationService {

    private final MovementRepository movementRepository;
    private final ProjectorRepository projectorRepository;
    private final KeyRepository keyRepository;

    public CreateReservationService(
            MovementRepository movementRepository, ProjectorRepository projectorRepository, KeyRepository keyRepository) {
        this.movementRepository = movementRepository;
        this.projectorRepository = projectorRepository;
        this.keyRepository = keyRepository;
    }

    public MovementResult execute(CreateReservationCommand command) {
        if (ProfessorPendencyRules.hasBlockingPendency(
                command.professorRegistrationNumber(), movementRepository, null)) {
            throw new BusinessRuleException("Professor has pending issues and cannot reserve (RN05).");
        }

        if (movementRepository.countActiveByProfessorAndAssetType(
                        command.professorRegistrationNumber(), command.assetType())
                >= 1) {
            throw new BusinessRuleException(
                    "Professor already has an active reservation or loan for this asset type.");
        }

        if (movementRepository
                .findOpenByAsset(command.assetType(), command.assetId(), MovementType.RESERVATION)
                .isPresent()) {
            throw new BusinessRuleException("Asset already has an open reservation.");
        }

        AssetInventory.requireAvailable(
                command.assetType(), command.assetId(), projectorRepository, keyRepository);

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
                projectorRepository,
                keyRepository);

        return MovementResult.from(movementRepository.save(reservation));
    }
}
