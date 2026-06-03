package com.gac.api.core.gateway;

import com.gac.api.core.domain.AssetType;
import com.gac.api.core.domain.Movement;
import com.gac.api.core.domain.MovementStatus;
import com.gac.api.core.domain.MovementType;
import java.util.List;
import java.util.Optional;

public interface MovementGateway {

    Movement save(Movement movement);

    List<Movement> findAll();

    Optional<Movement> findById(Long id);

    List<Movement> findByProfessorRegistrationNumber(String registrationNumber);

    Optional<Movement> findOpenByAsset(AssetType assetType, Long assetId, MovementType type);

    List<Movement> findOpenByProfessorAndType(String registrationNumber, MovementType type);

    long countActiveByProfessorAndAssetType(String registrationNumber, AssetType assetType);
}
