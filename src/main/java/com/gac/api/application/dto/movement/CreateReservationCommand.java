package com.gac.api.application.dto.movement;

import com.gac.api.domain.model.AssetType;

public record CreateReservationCommand(
        String professorRegistrationNumber, AssetType assetType, Long assetId, String academicPurpose) {}
