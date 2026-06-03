package com.gac.api.application.port.in.movement;

import com.gac.api.domain.model.MovementReport;
import java.time.LocalDate;

public interface GenerateMovementReportInputPort {
    MovementReport execute(LocalDate from, LocalDate to);
}
