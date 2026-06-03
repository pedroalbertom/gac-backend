package com.gac.api.application.port.in.projector;

import com.gac.api.domain.model.Projector;

public interface UpdateProjectorInputPort {
    Projector execute(Long id, Projector updatedData);
}
