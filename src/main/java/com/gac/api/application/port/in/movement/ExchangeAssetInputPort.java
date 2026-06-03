package com.gac.api.application.port.in.movement;

import com.gac.api.application.dto.movement.ExchangeAssetCommand;
import com.gac.api.application.dto.movement.MovementResult;

public interface ExchangeAssetInputPort {
    MovementResult execute(ExchangeAssetCommand command);
}
