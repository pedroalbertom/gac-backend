package com.gac.api.domain.model;

public class Pendency {

    private PendencyType type;
    private Long movementId;
    private String message;

    public Pendency() {
    }

    public Pendency(PendencyType type, Long movementId, String message) {
        this.type = type;
        this.movementId = movementId;
        this.message = message;
    }

    public PendencyType getType() {
        return type;
    }

    public void setType(PendencyType type) {
        this.type = type;
    }

    public Long getMovementId() {
        return movementId;
    }

    public void setMovementId(Long movementId) {
        this.movementId = movementId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
