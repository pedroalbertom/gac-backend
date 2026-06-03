package com.gac.api.application.port.in.projector;

import com.gac.api.domain.model.Projector;

public interface GetProjectorByIdInputPort {
    Projector execute(Long id);
}
