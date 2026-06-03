package com.gac.api.adapter.out.persistence.movement;

import com.gac.api.domain.model.AssetType;
import com.gac.api.domain.model.MovementStatus;
import com.gac.api.domain.model.MovementType;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    List<MovementEntity> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("""
            SELECT m FROM MovementEntity m
            WHERE (m.createdAt BETWEEN :start AND :end)
               OR (m.checkedOutAt BETWEEN :start AND :end)
               OR (m.returnedAt BETWEEN :start AND :end)
            """)
    List<MovementEntity> findInPeriod(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
