package com.gac.api.infrastructure.persistence.movement;

import com.gac.api.domain.exception.NotFoundException;
import com.gac.api.domain.model.AssetType;
import com.gac.api.domain.model.Movement;
import com.gac.api.domain.model.MovementStatus;
import com.gac.api.domain.model.MovementType;
import com.gac.api.application.repository.MovementRepository;
import com.gac.api.infrastructure.persistence.user.UserEntity;
import com.gac.api.infrastructure.persistence.user.UserJpaRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MovementRepositoryImpl implements MovementRepository {

    private final MovementJpaRepository jpaRepository;
    private final UserJpaRepository userJpaRepository;

    public MovementRepositoryImpl(MovementJpaRepository jpaRepository, UserJpaRepository userJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Movement save(Movement movement) {
        return toDomain(jpaRepository.save(toEntity(movement)));
    }

    @Override
    public List<Movement> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Movement> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Movement> findByProfessorRegistrationNumber(String registrationNumber) {
        return jpaRepository.findByProfessorRegistrationNumber(registrationNumber).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Movement> findOpenByAsset(AssetType assetType, Long assetId, MovementType type) {
        return jpaRepository.findByAssetTypeAndAssetIdAndTypeAndStatus(assetType, assetId, type, MovementStatus.OPEN)
                .stream()
                .findFirst()
                .map(this::toDomain);
    }

    @Override
    public List<Movement> findOpenByProfessorAndType(String registrationNumber, MovementType type) {
        return jpaRepository
                .findByProfessorRegistrationNumberAndTypeAndStatus(
                        registrationNumber, type, MovementStatus.OPEN)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long countActiveByProfessorAndAssetType(String registrationNumber, AssetType assetType) {
        return jpaRepository.countByProfessorRegistrationNumberAndAssetTypeAndStatusAndTypeIn(
                registrationNumber, assetType, MovementStatus.OPEN, List.of(MovementType.RESERVATION, MovementType.LOAN));
    }

    @Override
    public List<Movement> findByTypeAndStatus(MovementType type, MovementStatus status) {
        return jpaRepository.findByTypeAndStatus(type, status).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Movement> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end) {
        return jpaRepository.findByCreatedAtBetween(start, end).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Movement> findInPeriod(LocalDateTime start, LocalDateTime end) {
        return jpaRepository.findInPeriod(start, end).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private MovementEntity toEntity(Movement movement) {
        UserEntity attendantEntity = null;
        if (movement.getAttendantId() != null) {
            attendantEntity = userJpaRepository
                    .findById(movement.getAttendantId())
                    .orElseThrow(() -> new NotFoundException("Attendant not found."));
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
        entity.setReturnedAccessories(
                movement.getReturnedAccessories() != null
                        ? new ArrayList<>(movement.getReturnedAccessories())
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
        movement.setReturnedAccessories(
                entity.getReturnedAccessories() != null
                        ? new ArrayList<>(entity.getReturnedAccessories())
                        : new ArrayList<>());
        return movement;
    }
}
