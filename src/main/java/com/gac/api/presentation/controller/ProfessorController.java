package com.gac.api.presentation.controller;

import com.gac.api.core.usecase.user.CreateProfessorUseCase;
import com.gac.api.core.usecase.user.ListProfessorsUseCase;
import com.gac.api.presentation.dto.request.CreateProfessorRequest;
import com.gac.api.presentation.dto.response.UserResponse;
import com.gac.api.presentation.mapper.UserMapper;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/professors")
public class ProfessorController {

    private final CreateProfessorUseCase createProfessorUseCase;
    private final ListProfessorsUseCase listProfessorsUseCase;

    public ProfessorController(CreateProfessorUseCase createProfessorUseCase, ListProfessorsUseCase listProfessorsUseCase) {
        this.createProfessorUseCase = createProfessorUseCase;
        this.listProfessorsUseCase = listProfessorsUseCase;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','ATTENDANT')")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateProfessorRequest request) {
        var created = createProfessorUseCase.execute(UserMapper.fromProfessorRequest(request));
        URI location = URI.create("/api/professors/" + created.getId());
        return ResponseEntity.created(location).body(UserMapper.toResponse(created));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ATTENDANT')")
    public ResponseEntity<List<UserResponse>> list() {
        List<UserResponse> professors =
                listProfessorsUseCase.execute().stream().map(UserMapper::toResponse).toList();
        return ResponseEntity.ok(professors);
    }
}
