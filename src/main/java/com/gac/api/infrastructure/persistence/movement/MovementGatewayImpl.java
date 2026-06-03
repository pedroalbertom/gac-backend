package com.gac.api.infrastructure.persistence.movement;

import com.gac.api.core.domain.AssetType;
import com.gac.api.core.domain.Movement;
import com.gac.api.core.domain.MovementStatus;
import com.gac.api.core.domain.MovementType;
import com.gac.api.core.gateway.MovementGateway;
import com.gac.api.infrastructure.persistence.user.UserEntity;
import com.gac.api.infrastructure.persistence.user.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MovementGatewayImpl implements MovementGateway {

    private final MovementRepository repository;
    private final UserRepository userRepository;

    public MovementGatewayImpl(MovementRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public Movement save(Movement movement) {
        return toDomain(repository.save(toEntity(movement)));
    }

    @Override
    public List<Movement> findAll() {
        return repository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Movement> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Movement> findByProfessorRegistrationNumber(String registrationNumber) {
        return repository.findByProfessorRegistrationNumber(registrationNumber).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Movement> findOpenByAsset(AssetType assetType, Long assetId, MovementType type) {
        return repository.findByAssetTypeAndAssetIdAndTypeAndStatus(assetType, assetId, type, MovementStatus.OPEN)
                .stream()
                .findFirst()
                .map(this::toDomain);
    }

    @Override
    public List<Movement> findOpenByProfessorAndType(String registrationNumber, MovementType type) {
        return repository
                .findByProfessorRegistrationNumberAndTypeAndStatus(
                        registrationNumber, type, MovementStatus.OPEN)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long countActiveByProfessorAndAssetType(String registrationNumber, AssetType assetType) {
        return repository.countByProfessorRegistrationNumberAndAssetTypeAndStatusAndTypeIn(
                registrationNumber, assetType, MovementStatus.OPEN, List.of(MovementType.RESERVATION, MovementType.LOAN));
    }

    private MovementEntity toEntity(Movement movement) {
        UserEntity attendantEntity = null;
        if (movement.getAttendantId() != null) {
            attendantEntity = userRepository
                    .findById(movement.getAttendantId())
                    .orElseThrow(() -> new RuntimeException("Attendant not found."));
        }

        MovementEntity entity = new MovementEntity();
        entity.setId(movement.getId());
        entity.setType(movement.getType());
        entity.setStatus(movement.getStatus());
        entity.setProfessorRegistrationNumber(movement.getProfessorRegistrationNumber());
        entity.setAttendant(attendantEntity);
        entity.setAssetType(movement.getAssetType());
        entity.setAssetId(movement.getAssetId());
        entity.setConfirmationCode(movement.getConfirmationCode());
        entity.setAcademicPurpose(movement.getAcademicPurpose());
        entity.setRoom(movement.getRoom());
        entity.setDefectDescription(movement.getDefectDescription());
        entity.setCheckedOutAt(movement.getCheckedOutAt());
        entity.setReturnedAt(movement.getReturnedAt());
        entity.setCreatedAt(movement.getCreatedAt());
        entity.setLoanedAccessories(
                movement.getLoanedAccessories() != null
                        ? new ArrayList<>(movement.getLoanedAccessories())
                        : new ArrayList<>());
        return entity;
    }

    private Movement toDomain(MovementEntity entity) {
        Movement movement = new Movement();
        movement.setId(entity.getId());
        movement.setType(entity.getType());
        movement.setStatus(entity.getStatus());
        movement.setProfessorRegistrationNumber(entity.getProfessorRegistrationNumber());
        if (entity.getAttendant() != null) {
            movement.setAttendantId(entity.getAttendant().getId());
        }
        movement.setAssetType(entity.getAssetType());
        movement.setAssetId(entity.getAssetId());
        movement.setConfirmationCode(entity.getConfirmationCode());
        movement.setAcademicPurpose(entity.getAcademicPurpose());
        movement.setRoom(entity.getRoom());
        movement.setDefectDescription(entity.getDefectDescription());
        movement.setCheckedOutAt(entity.getCheckedOutAt());
        movement.setReturnedAt(entity.getReturnedAt());
        movement.setCreatedAt(entity.getCreatedAt());
        movement.setLoanedAccessories(
                entity.getLoanedAccessories() != null
                        ? new ArrayList<>(entity.getLoanedAccessories())
                        : new ArrayList<>());
        return movement;
    }
}
