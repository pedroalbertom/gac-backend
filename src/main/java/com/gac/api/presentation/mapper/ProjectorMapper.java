package com.gac.api.presentation.mapper;

import com.gac.api.core.domain.Projector;
import com.gac.api.presentation.dto.request.CreateProjectorRequest;
import com.gac.api.presentation.dto.request.UpdateProjectorRequest;
import com.gac.api.presentation.dto.response.ProjectorResponse;

public final class ProjectorMapper {

    private ProjectorMapper() {
    }

    public static ProjectorResponse toResponse(Projector projector) {
        return new ProjectorResponse(
                projector.getId(),
                projector.getBrand(),
                projector.getModel(),
                projector.getSerialNumber(),
                projector.getAssetTag(),
                projector.getStatus(),
                projector.getReservedRegistrationNumber(),
                projector.getDefectDescription());
    }

    public static Projector fromCreateRequest(CreateProjectorRequest request) {
        Projector projector = new Projector();
        projector.setBrand(request.brand());
        projector.setModel(request.model());
        projector.setSerialNumber(request.serialNumber());
        projector.setAssetTag(request.assetTag());
        return projector;
    }

    public static Projector fromUpdateRequest(UpdateProjectorRequest request) {
        Projector projector = new Projector();
        projector.setBrand(request.brand());
        projector.setModel(request.model());
        projector.setSerialNumber(request.serialNumber());
        projector.setAssetTag(request.assetTag());
        return projector;
    }
}
