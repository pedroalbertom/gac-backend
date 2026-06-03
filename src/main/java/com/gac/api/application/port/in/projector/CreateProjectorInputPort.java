package com.gac.api.application.port.in.projector;

import com.gac.api.domain.model.Projector;

public interface CreateProjectorInputPort {
    Projector execute(Projector newProjector);
}
