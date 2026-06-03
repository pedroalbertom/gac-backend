package com.gac.api.application.port.in.movement;

import com.gac.api.domain.model.Movement;
import com.gac.api.domain.model.User;
import java.util.List;

public interface RegisterReturnInputPort {
    Movement execute(Long loanId, User attendant, boolean hasDefect, String defectDescription, List<String> returnedAccessories);
}
