package com.gac.api.presentation.dto.response;

import com.gac.api.core.domain.PendencyType;

public record PendencyResponse(PendencyType type, Long movementId, String message) {
}
