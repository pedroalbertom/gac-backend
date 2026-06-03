package com.gac.api.adapter.in.web.controller;

import com.gac.api.application.port.in.user.CreateProfessorInputPort;
import com.gac.api.application.port.in.user.ListProfessorsInputPort;
import com.gac.api.adapter.in.web.dto.request.CreateProfessorRequest;
import com.gac.api.adapter.in.web.dto.response.UserResponse;
import com.gac.api.adapter.in.web.mapper.UserMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Professors", description = "Professor registration (UC02)")
public class ProfessorController {

    private final CreateProfessorInputPort createProfessorUseCase;
    private final ListProfessorsInputPort listProfessorsUseCase;

    public ProfessorController(CreateProfessorInputPort createProfessorUseCase, ListProfessorsInputPort listProfessorsUseCase) {
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
