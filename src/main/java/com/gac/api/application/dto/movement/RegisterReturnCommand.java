package com.gac.api.application.dto.movement;

import java.util.List;

public record RegisterReturnCommand(
        Long loanId,
        Long attendantId,
        boolean hasDefect,
        String defectDescription,
        List<String> returnedAccessories) {}
