package com.gac.api.adapter.in.web.dto.response;

import java.time.LocalDate;
import java.util.List;

public record MovementReportResponse(LocalDate from, LocalDate to, int totalCount, List<MovementResponse> movements) {
}
