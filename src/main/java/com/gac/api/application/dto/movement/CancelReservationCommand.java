package com.gac.api.application.dto.movement;

public record CancelReservationCommand(Long reservationId, String professorRegistrationNumber) {}
