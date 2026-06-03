package com.gac.api.application.service.movement;

import org.springframework.stereotype.Service;

import com.gac.api.domain.exception.BusinessRuleException;
import com.gac.api.domain.model.Movement;
import com.gac.api.domain.model.MovementReport;
import com.gac.api.application.repository.MovementRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;

@Service
public class GenerateMovementReportService {

    private final MovementRepository movementRepository;

    public GenerateMovementReportService(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    public MovementReport execute(LocalDate from, LocalDate to) {
        if (from == null || to == null) {
            throw new BusinessRuleException("Report period start and end dates are required.");
        }
        if (from.isAfter(to)) {
            throw new BusinessRuleException("Report start date must be on or before end date.");
        }

        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(23, 59, 59);

        var movements = movementRepository.findInPeriod(start, end).stream()
                .sorted(Comparator.comparing(
                                Movement::eventAt,
                                Comparator.nullsFirst(Comparator.naturalOrder()))
                        .thenComparing(Movement::getId, Comparator.nullsFirst(Comparator.naturalOrder())))
                .toList();

        return new MovementReport(from, to, movements);
    }
}
