package com.gac.api.infrastructure.persistence.projector;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectorRepository extends JpaRepository<ProjectorEntity, Long> {

    Optional<ProjectorEntity> findByAssetTag(String assetTag);
}
