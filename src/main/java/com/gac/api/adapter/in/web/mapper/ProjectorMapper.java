package com.gac.api.adapter.in.web.mapper;

import com.gac.api.domain.model.Projector;
import com.gac.api.adapter.in.web.dto.request.CreateProjectorRequest;
import com.gac.api.adapter.in.web.dto.request.UpdateProjectorRequest;
import com.gac.api.adapter.in.web.dto.response.ProjectorResponse;

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
