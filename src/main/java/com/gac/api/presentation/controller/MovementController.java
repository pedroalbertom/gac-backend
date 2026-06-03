package com.gac.api.presentation.controller;

import com.gac.api.core.usecase.movement.ConfirmLoanUseCase;
import com.gac.api.core.usecase.movement.ExchangeAssetUseCase;
import com.gac.api.core.usecase.movement.FindMovementsByProfessorUseCase;
import com.gac.api.core.usecase.movement.FindProfessorPendenciesUseCase;
import com.gac.api.core.usecase.movement.ListActiveLoansUseCase;
import com.gac.api.core.usecase.movement.RegisterReturnUseCase;
import com.gac.api.core.usecase.user.GetUserByIdUseCase;
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

    private final ConfirmLoanUseCase confirmLoanUseCase;
    private final RegisterReturnUseCase registerReturnUseCase;
    private final ListActiveLoansUseCase listActiveLoansUseCase;
    private final FindMovementsByProfessorUseCase findMovementsByProfessorUseCase;
    private final FindProfessorPendenciesUseCase findProfessorPendenciesUseCase;
    private final ExchangeAssetUseCase exchangeAssetUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;

    public MovementController(
            ConfirmLoanUseCase confirmLoanUseCase,
            RegisterReturnUseCase registerReturnUseCase,
            ListActiveLoansUseCase listActiveLoansUseCase,
            FindMovementsByProfessorUseCase findMovementsByProfessorUseCase,
            FindProfessorPendenciesUseCase findProfessorPendenciesUseCase,
            ExchangeAssetUseCase exchangeAssetUseCase,
            GetUserByIdUseCase getUserByIdUseCase) {
        this.confirmLoanUseCase = confirmLoanUseCase;
        this.registerReturnUseCase = registerReturnUseCase;
        this.listActiveLoansUseCase = listActiveLoansUseCase;
        this.findMovementsByProfessorUseCase = findMovementsByProfessorUseCase;
        this.findProfessorPendenciesUseCase = findProfessorPendenciesUseCase;
        this.exchangeAssetUseCase = exchangeAssetUseCase;
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
                request.loanId(),
                attendant,
                request.hasDefect(),
                request.defectDescription(),
                request.returnedAccessories());
        URI location = URI.create("/api/movements/" + returnMovement.getId());
        return ResponseEntity.created(location).body(MovementMapper.toResponse(returnMovement));
    }

    @PostMapping("/exchanges")
    @PreAuthorize("hasAnyRole('ADMIN','ATTENDANT')")
    public ResponseEntity<MovementResponse> exchangeAsset(
            @AuthenticationPrincipal JwtUserPrincipal principal, @Valid @RequestBody ExchangeAssetRequest request) {
        var attendant = getUserByIdUseCase.execute(principal.userId());
        var newLoan = exchangeAssetUseCase.execute(
                request.loanId(),
                request.substituteAssetType(),
                request.substituteAssetId(),
                request.defectDescription(),
                attendant,
                request.room(),
                request.loanedAccessories());
        URI location = URI.create("/api/movements/" + newLoan.getId());
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
