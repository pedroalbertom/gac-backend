package com.gac.api.adapter.in.web.dto.response;

import com.gac.api.domain.model.PendencyType;

public record PendencyResponse(PendencyType type, Long movementId, String message) {
}
