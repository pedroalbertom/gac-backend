package com.gac.api.adapter.in.web.controller;

import com.gac.api.application.dto.movement.ConfirmLoanCommand;
import com.gac.api.application.dto.movement.RegisterReturnCommand;
import com.gac.api.application.port.in.movement.ConfirmLoanInputPort;
import com.gac.api.application.port.in.movement.ExchangeAssetInputPort;
import com.gac.api.application.port.in.movement.FindMovementsByProfessorInputPort;
import com.gac.api.application.port.in.movement.FindProfessorPendenciesInputPort;
import com.gac.api.application.port.in.movement.ListActiveLoansInputPort;
import com.gac.api.application.port.in.movement.RegisterReturnInputPort;
import com.gac.api.application.port.in.user.GetUserByIdInputPort;
import com.gac.api.adapter.out.security.JwtUserPrincipal;
import com.gac.api.adapter.in.web.dto.request.ConfirmLoanRequest;
import com.gac.api.adapter.in.web.dto.request.ExchangeAssetRequest;
import com.gac.api.adapter.in.web.dto.request.ReturnRequest;
import com.gac.api.adapter.in.web.dto.response.MovementResponse;
import com.gac.api.adapter.in.web.dto.response.PendencyResponse;
import com.gac.api.adapter.in.web.mapper.MovementMapper;
import com.gac.api.adapter.in.web.mapper.PendencyMapper;
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

    private final ConfirmLoanInputPort confirmLoanUseCase;
    private final RegisterReturnInputPort registerReturnUseCase;
    private final ListActiveLoansInputPort listActiveLoansUseCase;
    private final FindMovementsByProfessorInputPort findMovementsByProfessorUseCase;
    private final FindProfessorPendenciesInputPort findProfessorPendenciesUseCase;
    private final ExchangeAssetInputPort exchangeAssetUseCase;
    private final GetUserByIdInputPort getUserByIdUseCase;

    public MovementController(
            ConfirmLoanInputPort confirmLoanUseCase,
            RegisterReturnInputPort registerReturnUseCase,
            ListActiveLoansInputPort listActiveLoansUseCase,
            FindMovementsByProfessorInputPort findMovementsByProfessorUseCase,
            FindProfessorPendenciesInputPort findProfessorPendenciesUseCase,
            ExchangeAssetInputPort exchangeAssetUseCase,
            GetUserByIdInputPort getUserByIdUseCase) {
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
