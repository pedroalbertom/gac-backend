package com.gac.api.application.port.in.movement;

import com.gac.api.application.dto.movement.ConfirmLoanCommand;
import com.gac.api.application.dto.movement.MovementResult;

public interface ConfirmLoanInputPort {
    MovementResult execute(ConfirmLoanCommand command);
}
