package com.gac.api.presentation.controller;

import com.gac.api.core.usecase.movement.GenerateMovementReportUseCase;
import com.gac.api.presentation.dto.response.MovementReportResponse;
import com.gac.api.presentation.mapper.MovementMapper;
import com.gac.api.presentation.mapper.MovementReportCsvMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@Tag(name = "Reports", description = "Movement reports and export (UC06, RF11)")
public class ReportController {

    private final GenerateMovementReportUseCase generateMovementReportUseCase;

    public ReportController(GenerateMovementReportUseCase generateMovementReportUseCase) {
        this.generateMovementReportUseCase = generateMovementReportUseCase;
    }

    @GetMapping(value = "/movements", produces = {MediaType.APPLICATION_JSON_VALUE, "text/csv"})
    @PreAuthorize("hasAnyRole('ADMIN','ATTENDANT')")
    public ResponseEntity<?> movementReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(defaultValue = "json") String format) {
        var report = generateMovementReportUseCase.execute(from, to);
        var movements = report.getMovements().stream().map(MovementMapper::toResponse).toList();

        if ("csv".equalsIgnoreCase(format)) {
            String csv = MovementReportCsvMapper.toCsv(movements);
            String filename = "movements-" + from + "_" + to + ".csv";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentType(MediaType.parseMediaType("text/csv"))
                    .body(csv);
        }

        return ResponseEntity.ok(new MovementReportResponse(from, to, movements.size(), movements));
    }
}
