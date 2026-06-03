package com.gac.api.presentation.controller;

import com.gac.api.core.usecase.movement.GenerateMovementReportUseCase;
import com.gac.api.presentation.dto.response.MovementReportResponse;
import com.gac.api.presentation.mapper.MovementMapper;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final GenerateMovementReportUseCase generateMovementReportUseCase;

    public ReportController(GenerateMovementReportUseCase generateMovementReportUseCase) {
        this.generateMovementReportUseCase = generateMovementReportUseCase;
    }

    @GetMapping("/movements")
    @PreAuthorize("hasAnyRole('ADMIN','ATTENDANT')")
    public ResponseEntity<MovementReportResponse> movementReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        var report = generateMovementReportUseCase.execute(from, to);
        var movements = report.getMovements().stream().map(MovementMapper::toResponse).toList();
        return ResponseEntity.ok(new MovementReportResponse(from, to, movements.size(), movements));
    }
}
