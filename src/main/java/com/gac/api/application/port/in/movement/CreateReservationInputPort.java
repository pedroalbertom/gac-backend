package com.gac.api.application.port.in.movement;

import com.gac.api.domain.model.AssetType;
import com.gac.api.domain.model.Movement;

public interface CreateReservationInputPort {
    Movement execute(String professorRegistrationNumber, AssetType assetType, Long assetId, String academicPurpose);
}
