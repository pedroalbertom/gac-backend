package com.gac.api.infrastructure.persistence.projector;

import com.gac.api.core.domain.Projector;
import com.gac.api.core.gateway.ProjectorGateway;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ProjectorGatewayImpl implements ProjectorGateway {

    private final ProjectorRepository repository;

    public ProjectorGatewayImpl(ProjectorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Projector save(Projector projector) {
        return toDomain(repository.save(toEntity(projector)));
    }

    @Override
    public List<Projector> findAll() {
        return repository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<Projector> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Projector> findByAssetTag(String assetTag) {
        return repository.findByAssetTag(assetTag).map(this::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private ProjectorEntity toEntity(Projector projector) {
        return new ProjectorEntity(
                projector.getId(),
                projector.getBrand(),
                projector.getModel(),
                projector.getSerialNumber(),
                projector.getAssetTag(),
                projector.getStatus(),
                projector.getReservedRegistrationNumber(),
                projector.getDefectDescription());
    }

    private Projector toDomain(ProjectorEntity entity) {
        return new Projector(
                entity.getId(),
                entity.getBrand(),
                entity.getModel(),
                entity.getSerialNumber(),
                entity.getAssetTag(),
                entity.getStatus(),
                entity.getReservedRegistrationNumber(),
                entity.getDefectDescription());
    }
}
