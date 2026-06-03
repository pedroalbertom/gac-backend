package com.gac.api.application.port.in.projector;

import com.gac.api.domain.model.Projector;
import java.util.List;

public interface ListProjectorsInputPort {
    List<Projector> execute();
}
