package com.gac.api.core.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Movement {

    private Long id;
    private LocalDateTime dateTime;
    private String professorRegistrationNumber;
    private String room;
    private User attendant;
    private MovementType type;
    private List<Projector> projectors;
    private List<Key> keys;

    public Movement() {
    }

    public Movement(
            Long id,
            LocalDateTime dateTime,
            String professorRegistrationNumber,
            String room,
            User attendant,
            MovementType type,
            List<Projector> projectors,
            List<Key> keys) {
        this.id = id;
        this.dateTime = dateTime;
        this.professorRegistrationNumber = professorRegistrationNumber;
        this.room = room;
        this.attendant = attendant;
        this.type = type;
        this.projectors = projectors;
        this.keys = keys;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getProfessorRegistrationNumber() {
        return professorRegistrationNumber;
    }

    public void setProfessorRegistrationNumber(String professorRegistrationNumber) {
        this.professorRegistrationNumber = professorRegistrationNumber;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public User getAttendant() {
        return attendant;
    }

    public void setAttendant(User attendant) {
        this.attendant = attendant;
    }

    public MovementType getType() {
        return type;
    }

    public void setType(MovementType type) {
        this.type = type;
    }

    public List<Projector> getProjectors() {
        return projectors;
    }

    public void setProjectors(List<Projector> projectors) {
        this.projectors = projectors;
    }

    public List<Key> getKeys() {
        return keys;
    }

    public void setKeys(List<Key> keys) {
        this.keys = keys;
    }
}
