package com.gac.api.application.usecase.movement;

import com.gac.api.application.port.in.movement.GenerateMovementReportInputPort;

import com.gac.api.domain.model.Movement;
import com.gac.api.domain.model.MovementReport;
import com.gac.api.application.port.out.MovementGateway;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;

public class GenerateMovementReportUseCase implements GenerateMovementReportInputPort {

    private final MovementGateway movementGateway;

    public GenerateMovementReportUseCase(MovementGateway movementGateway) {
        this.movementGateway = movementGateway;
    }

    public MovementReport execute(LocalDate from, LocalDate to) {
        if (from == null || to == null) {
            throw new RuntimeException("Report period start and end dates are required.");
        }
        if (from.isAfter(to)) {
            throw new RuntimeException("Report start date must be on or before end date.");
        }

        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(23, 59, 59);

        var movements = movementGateway.findInPeriod(start, end).stream()
                .sorted(Comparator.comparing(
                                Movement::eventAt,
                                Comparator.nullsFirst(Comparator.naturalOrder()))
                        .thenComparing(Movement::getId, Comparator.nullsFirst(Comparator.naturalOrder())))
                .toList();

        return new MovementReport(from, to, movements);
    }
}
