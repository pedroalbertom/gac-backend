package com.gac.api.application.port.in.movement;

import com.gac.api.application.dto.movement.CancelReservationCommand;
import com.gac.api.application.dto.movement.MovementResult;

public interface CancelReservationInputPort {
    MovementResult execute(CancelReservationCommand command);
}
