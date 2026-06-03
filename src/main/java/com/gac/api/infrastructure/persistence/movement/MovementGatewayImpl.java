package com.gac.api.infrastructure.persistence.movement;

import com.gac.api.core.domain.Key;
import com.gac.api.core.domain.Movement;
import com.gac.api.core.domain.Projector;
import com.gac.api.core.domain.User;
import com.gac.api.core.gateway.MovementGateway;
import com.gac.api.infrastructure.persistence.key.KeyEntity;
import com.gac.api.infrastructure.persistence.projector.ProjectorEntity;
import com.gac.api.infrastructure.persistence.user.UserEntity;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MovementGatewayImpl implements MovementGateway {

    private final MovementRepository repository;

    public MovementGatewayImpl(MovementRepository repository) {
        this.repository = repository;
    }

    @Override
    public Movement save(Movement movement) {
        List<Projector> movementProjectors =
                movement.getProjectors() != null ? movement.getProjectors() : List.of();
        List<Key> movementKeys = movement.getKeys() != null ? movement.getKeys() : List.of();

        List<ProjectorEntity> projectorEntities = movementProjectors.stream()
                .map(p -> new ProjectorEntity(
                        p.getId(), p.getBrand(), p.getModel(), p.getAssetTag(), p.getStatus()))
                .collect(Collectors.toList());

        List<KeyEntity> keyEntities = movementKeys.stream()
                .map(k -> new KeyEntity(k.getId(), k.getRoom(), k.getBlock(), k.getStatus()))
                .collect(Collectors.toList());

        User attendant = movement.getAttendant();
        UserEntity attendantEntity = new UserEntity(
                attendant.getId(),
                attendant.getName(),
                attendant.getEmail(),
                attendant.getRegistrationNumber(),
                attendant.getPassword(),
                attendant.getRole());

        MovementEntity entity = new MovementEntity(
                movement.getId(),
                movement.getDateTime(),
                movement.getProfessorRegistrationNumber(),
                movement.getRoom(),
                attendantEntity,
                movement.getType(),
                projectorEntities,
                keyEntities);

        return toDomain(repository.save(entity));
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
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private Movement toDomain(MovementEntity entity) {
        List<ProjectorEntity> projectorEntities =
                entity.getProjectors() != null ? entity.getProjectors() : List.of();
        List<KeyEntity> keyEntities = entity.getKeys() != null ? entity.getKeys() : List.of();

        List<Projector> projectors = projectorEntities.stream()
                .map(p -> new Projector(
                        p.getId(), p.getBrand(), p.getModel(), p.getAssetTag(), p.getStatus()))
                .collect(Collectors.toList());

        List<Key> keys = keyEntities.stream()
                .map(k -> new Key(k.getId(), k.getRoom(), k.getBlock(), k.getStatus()))
                .collect(Collectors.toList());

        UserEntity attendantEntity = entity.getAttendant();
        User attendant = new User(
                attendantEntity.getId(),
                attendantEntity.getName(),
                attendantEntity.getEmail(),
                attendantEntity.getRegistrationNumber(),
                attendantEntity.getPassword(),
                attendantEntity.getRole());

        return new Movement(
                entity.getId(),
                entity.getDateTime(),
                entity.getProfessorRegistrationNumber(),
                entity.getRoom(),
                attendant,
                entity.getType(),
                projectors,
                keys);
    }
}
