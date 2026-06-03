package com.gac.api.application.port.in.movement;

import com.gac.api.application.dto.movement.MovementResult;
import com.gac.api.application.dto.movement.RegisterReturnCommand;

public interface RegisterReturnInputPort {
    MovementResult execute(RegisterReturnCommand command);
}
