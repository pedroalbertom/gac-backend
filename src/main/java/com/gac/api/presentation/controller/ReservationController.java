package com.gac.api.presentation.controller;

import com.gac.api.core.usecase.movement.CancelReservationUseCase;
import com.gac.api.core.usecase.movement.CreateReservationUseCase;
import com.gac.api.core.usecase.movement.ListOpenReservationsUseCase;
import com.gac.api.infrastructure.security.JwtUserPrincipal;
import com.gac.api.presentation.dto.request.CreateReservationRequest;
import com.gac.api.presentation.dto.response.MovementResponse;
import com.gac.api.presentation.mapper.MovementMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
@Tag(name = "Reservations", description = "Professor reservations (UC11, RF13)")
public class ReservationController {

    private final CreateReservationUseCase createReservationUseCase;
    private final CancelReservationUseCase cancelReservationUseCase;
    private final ListOpenReservationsUseCase listOpenReservationsUseCase;

    public ReservationController(
            CreateReservationUseCase createReservationUseCase,
            CancelReservationUseCase cancelReservationUseCase,
            ListOpenReservationsUseCase listOpenReservationsUseCase) {
        this.createReservationUseCase = createReservationUseCase;
        this.cancelReservationUseCase = cancelReservationUseCase;
        this.listOpenReservationsUseCase = listOpenReservationsUseCase;
    }

    @PostMapping
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<MovementResponse> create(
            @AuthenticationPrincipal JwtUserPrincipal principal, @Valid @RequestBody CreateReservationRequest request) {
        var created = createReservationUseCase.execute(
                principal.registrationNumber(), request.assetType(), request.assetId(), request.academicPurpose());
        URI location = URI.create("/api/reservations/" + created.getId());
        return ResponseEntity.created(location).body(MovementMapper.toResponse(created));
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<List<MovementResponse>> myReservations(@AuthenticationPrincipal JwtUserPrincipal principal) {
        List<MovementResponse> reservations = listOpenReservationsUseCase.execute(principal.registrationNumber()).stream()
                .map(MovementMapper::toResponse)
                .toList();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ATTENDANT')")
    public ResponseEntity<List<MovementResponse>> listByProfessor(
            @RequestParam String professorRegistrationNumber) {
        List<MovementResponse> reservations =
                listOpenReservationsUseCase.execute(professorRegistrationNumber).stream()
                        .map(MovementMapper::toResponse)
                        .toList();
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<MovementResponse> cancel(
            @AuthenticationPrincipal JwtUserPrincipal principal, @PathVariable Long id) {
        var cancelled = cancelReservationUseCase.execute(id, principal.registrationNumber());
        return ResponseEntity.ok(MovementMapper.toResponse(cancelled));
    }
}
