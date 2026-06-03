package com.gac.api.infrastructure.persistence.movement;

import com.gac.api.core.domain.AssetType;
import com.gac.api.core.domain.MovementStatus;
import com.gac.api.core.domain.MovementType;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementRepository extends JpaRepository<MovementEntity, Long> {

    List<MovementEntity> findByProfessorRegistrationNumber(String professorRegistrationNumber);

    List<MovementEntity> findByAssetTypeAndAssetIdAndTypeAndStatus(
            AssetType assetType, Long assetId, MovementType type, MovementStatus status);

    List<MovementEntity> findByProfessorRegistrationNumberAndTypeAndStatus(
            String professorRegistrationNumber, MovementType type, MovementStatus status);

    long countByProfessorRegistrationNumberAndAssetTypeAndStatusAndTypeIn(
            String professorRegistrationNumber,
            AssetType assetType,
            MovementStatus status,
            Collection<MovementType> types);

    List<MovementEntity> findByTypeAndStatus(MovementType type, MovementStatus status);
}
