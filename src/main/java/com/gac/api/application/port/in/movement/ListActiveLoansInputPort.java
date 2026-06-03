package com.gac.api.application.port.in.movement;

import com.gac.api.domain.model.Movement;
import java.util.List;

public interface ListActiveLoansInputPort {
    List<Movement> execute();
}
