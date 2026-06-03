package com.gac.api.application.port.in.movement;

import com.gac.api.domain.model.Movement;
import com.gac.api.domain.model.User;
import java.util.List;

public interface ConfirmLoanInputPort {
    Movement execute(Long reservationId, String confirmationCode, User attendant, String room, List<String> loanedAccessories);
}
