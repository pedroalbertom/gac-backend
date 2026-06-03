package com.gac.api.application.port.in.movement;

import com.gac.api.application.dto.movement.CreateReservationCommand;
import com.gac.api.application.dto.movement.MovementResult;

public interface CreateReservationInputPort {
    MovementResult execute(CreateReservationCommand command);
}
