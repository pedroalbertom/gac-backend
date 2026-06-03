package com.gac.api.presentation.controller;

import com.gac.api.application.service.movement.ConfirmLoanService;
import com.gac.api.application.service.movement.ExchangeAssetService;
import com.gac.api.application.service.movement.FindMovementsByProfessorService;
import com.gac.api.application.service.movement.FindProfessorPendenciesService;
import com.gac.api.application.service.movement.ListActiveLoansService;
import com.gac.api.application.service.movement.RegisterReturnService;

import com.gac.api.application.dto.movement.ConfirmLoanCommand;
import com.gac.api.application.dto.movement.ExchangeAssetCommand;
import com.gac.api.application.dto.movement.RegisterReturnCommand;
import com.gac.api.infrastructure.security.JwtUserPrincipal;
import com.gac.api.presentation.dto.request.ConfirmLoanRequest;
import com.gac.api.presentation.dto.request.ExchangeAssetRequest;
import com.gac.api.presentation.dto.request.ReturnRequest;
import com.gac.api.presentation.dto.response.MovementResponse;
import com.gac.api.presentation.dto.response.PendencyResponse;
import com.gac.api.presentation.mapper.MovementMapper;
import com.gac.api.presentation.mapper.PendencyMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Movements", description = "Loans, returns, exchanges, and pendencies (UC03, UC04, UC05, UC12)")
public class MovementController {

    private final ConfirmLoanService confirmLoanUseCase;
    private final RegisterReturnService registerReturnUseCase;
    private final ListActiveLoansService listActiveLoansUseCase;
    private final FindMovementsByProfessorService findMovementsByProfessorUseCase;
    private final FindProfessorPendenciesService findProfessorPendenciesUseCase;
    private final ExchangeAssetService exchangeAssetUseCase;

    public MovementController(
            ConfirmLoanService confirmLoanUseCase,
            RegisterReturnService registerReturnUseCase,
            ListActiveLoansService listActiveLoansUseCase,
            FindMovementsByProfessorService findMovementsByProfessorUseCase,
            FindProfessorPendenciesService findProfessorPendenciesUseCase,
            ExchangeAssetService exchangeAssetUseCase) {
        this.confirmLoanUseCase = confirmLoanUseCase;
        this.registerReturnUseCase = registerReturnUseCase;
        this.listActiveLoansUseCase = listActiveLoansUseCase;
        this.findMovementsByProfessorUseCase = findMovementsByProfessorUseCase;
        this.findProfessorPendenciesUseCase = findProfessorPendenciesUseCase;
        this.exchangeAssetUseCase = exchangeAssetUseCase;
    }

    @PostMapping("/loans")
    @PreAuthorize("hasAnyRole('ADMIN','ATTENDANT')")
    public ResponseEntity<MovementResponse> confirmLoan(
            @AuthenticationPrincipal JwtUserPrincipal principal, @Valid @RequestBody ConfirmLoanRequest request) {
        var loan = confirmLoanUseCase.execute(new ConfirmLoanCommand(
                request.reservationId(),
                request.confirmationCode(),
                principal.userId(),
                request.room(),
                request.loanedAccessories()));
        URI location = URI.create("/api/movements/" + loan.id());
        return ResponseEntity.created(location).body(MovementMapper.toResponse(loan));
    }

    @PostMapping("/returns")
    @PreAuthorize("hasAnyRole('ADMIN','ATTENDANT')")
    public ResponseEntity<MovementResponse> registerReturn(
            @AuthenticationPrincipal JwtUserPrincipal principal, @Valid @RequestBody ReturnRequest request) {
        var returnMovement = registerReturnUseCase.execute(new RegisterReturnCommand(
                request.loanId(),
                principal.userId(),
                request.hasDefect(),
                request.defectDescription(),
                request.returnedAccessories()));
        URI location = URI.create("/api/movements/" + returnMovement.id());
        return ResponseEntity.created(location).body(MovementMapper.toResponse(returnMovement));
    }

    @PostMapping("/exchanges")
    @PreAuthorize("hasAnyRole('ADMIN','ATTENDANT')")
    public ResponseEntity<MovementResponse> exchangeAsset(
            @AuthenticationPrincipal JwtUserPrincipal principal, @Valid @RequestBody ExchangeAssetRequest request) {
        var newLoan = exchangeAssetUseCase.execute(new ExchangeAssetCommand(
                request.loanId(),
                request.substituteAssetType(),
                request.substituteAssetId(),
                request.defectDescription(),
                principal.userId(),
                request.room(),
                request.loanedAccessories()));
        URI location = URI.create("/api/movements/" + newLoan.id());
        return ResponseEntity.created(location).body(MovementMapper.toResponse(newLoan));
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

    @GetMapping("/me/pending")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<List<PendencyResponse>> myPendencies(@AuthenticationPrincipal JwtUserPrincipal principal) {
        List<PendencyResponse> pendencies = findProfessorPendenciesUseCase.execute(principal.registrationNumber())
                .stream()
                .map(PendencyMapper::toResponse)
                .toList();
        return ResponseEntity.ok(pendencies);
    }
}
