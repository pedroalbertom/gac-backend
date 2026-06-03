package com.gac.api.infrastructure.persistence.projector;

import com.gac.api.domain.model.Projector;
import com.gac.api.application.repository.ProjectorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ProjectorRepositoryImpl implements ProjectorRepository {

    private final ProjectorJpaRepository jpaRepository;

    public ProjectorRepositoryImpl(ProjectorJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Projector save(Projector projector) {
        return toDomain(jpaRepository.save(toEntity(projector)));
    }

    @Override
    public List<Projector> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<Projector> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Projector> findByAssetTag(String assetTag) {
        return jpaRepository.findByAssetTag(assetTag).map(this::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
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
