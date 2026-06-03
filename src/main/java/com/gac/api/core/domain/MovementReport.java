package com.gac.api.core.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovementReport {

    private LocalDate from;
    private LocalDate to;
    private List<Movement> movements = new ArrayList<>();

    public MovementReport() {
    }

    public MovementReport(LocalDate from, LocalDate to, List<Movement> movements) {
        this.from = from;
        this.to = to;
        this.movements = movements != null ? movements : new ArrayList<>();
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public List<Movement> getMovements() {
        return movements;
    }

    public void setMovements(List<Movement> movements) {
        this.movements = movements != null ? movements : new ArrayList<>();
    }
}
