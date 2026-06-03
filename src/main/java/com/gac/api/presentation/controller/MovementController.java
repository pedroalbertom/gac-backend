package com.gac.api.presentation.controller;

import com.gac.api.core.usecase.movement.ConfirmLoanUseCase;
import com.gac.api.core.usecase.movement.FindMovementsByProfessorUseCase;
import com.gac.api.core.usecase.movement.ListActiveLoansUseCase;
import com.gac.api.core.usecase.movement.RegisterReturnUseCase;
import com.gac.api.core.usecase.user.GetUserByIdUseCase;
import com.gac.api.infrastructure.security.JwtUserPrincipal;
import com.gac.api.presentation.dto.request.ConfirmLoanRequest;
import com.gac.api.presentation.dto.request.ReturnRequest;
import com.gac.api.presentation.dto.response.MovementResponse;
import com.gac.api.presentation.mapper.MovementMapper;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movements")
public class MovementController {

    private final ConfirmLoanUseCase confirmLoanUseCase;
    private final RegisterReturnUseCase registerReturnUseCase;
    private final ListActiveLoansUseCase listActiveLoansUseCase;
    private final FindMovementsByProfessorUseCase findMovementsByProfessorUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;

    public MovementController(
            ConfirmLoanUseCase confirmLoanUseCase,
            RegisterReturnUseCase registerReturnUseCase,
            ListActiveLoansUseCase listActiveLoansUseCase,
            FindMovementsByProfessorUseCase findMovementsByProfessorUseCase,
            GetUserByIdUseCase getUserByIdUseCase) {
        this.confirmLoanUseCase = confirmLoanUseCase;
        this.registerReturnUseCase = registerReturnUseCase;
        this.listActiveLoansUseCase = listActiveLoansUseCase;
        this.findMovementsByProfessorUseCase = findMovementsByProfessorUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
    }

    @PostMapping("/loans")
    @PreAuthorize("hasAnyRole('ADMIN','ATTENDANT')")
    public ResponseEntity<MovementResponse> confirmLoan(
            @AuthenticationPrincipal JwtUserPrincipal principal, @Valid @RequestBody ConfirmLoanRequest request) {
        var attendant = getUserByIdUseCase.execute(principal.userId());
        var loan = confirmLoanUseCase.execute(
                request.reservationId(),
                request.confirmationCode(),
                attendant,
                request.room(),
                request.loanedAccessories());
        URI location = URI.create("/api/movements/" + loan.getId());
        return ResponseEntity.created(location).body(MovementMapper.toResponse(loan));
    }

    @PostMapping("/returns")
    @PreAuthorize("hasAnyRole('ADMIN','ATTENDANT')")
    public ResponseEntity<MovementResponse> registerReturn(
            @AuthenticationPrincipal JwtUserPrincipal principal, @Valid @RequestBody ReturnRequest request) {
        var attendant = getUserByIdUseCase.execute(principal.userId());
        var returnMovement = registerReturnUseCase.execute(
                request.loanId(), attendant, request.hasDefect(), request.defectDescription());
        URI location = URI.create("/api/movements/" + returnMovement.getId());
        return ResponseEntity.created(location).body(MovementMapper.toResponse(returnMovement));
    }

    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('ADMIN','ATTENDANT')")
    public ResponseEntity<List<MovementResponse>> activeLoans() {
        List<MovementResponse> loans = listActiveLoansUseCase.execute().stream()
                .map(MovementMapper::toResponse)
                .toList();
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<List<MovementResponse>> myMovements(@AuthenticationPrincipal JwtUserPrincipal principal) {
        List<MovementResponse> movements = findMovementsByProfessorUseCase.execute(principal.registrationNumber())
                .stream()
                .map(MovementMapper::toResponse)
                .toList();
        return ResponseEntity.ok(movements);
    }
}
