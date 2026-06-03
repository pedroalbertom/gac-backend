package com.gac.api.presentation.controller;

import com.gac.api.core.usecase.projector.CreateProjectorUseCase;
import com.gac.api.core.usecase.projector.DeleteProjectorUseCase;
import com.gac.api.core.usecase.projector.GetProjectorByIdUseCase;
import com.gac.api.core.usecase.projector.ListProjectorsUseCase;
import com.gac.api.core.usecase.projector.UpdateProjectorUseCase;
import com.gac.api.presentation.dto.request.CreateProjectorRequest;
import com.gac.api.presentation.dto.request.UpdateProjectorRequest;
import com.gac.api.presentation.dto.response.ProjectorResponse;
import com.gac.api.presentation.mapper.ProjectorMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projectors")
@Tag(name = "Projectors", description = "Projector CRUD (UC08, UC13, UC14)")
public class ProjectorController {

    private final CreateProjectorUseCase createProjectorUseCase;
    private final ListProjectorsUseCase listProjectorsUseCase;
    private final GetProjectorByIdUseCase getProjectorByIdUseCase;
    private final UpdateProjectorUseCase updateProjectorUseCase;
    private final DeleteProjectorUseCase deleteProjectorUseCase;

    public ProjectorController(
            CreateProjectorUseCase createProjectorUseCase,
            ListProjectorsUseCase listProjectorsUseCase,
            GetProjectorByIdUseCase getProjectorByIdUseCase,
            UpdateProjectorUseCase updateProjectorUseCase,
            DeleteProjectorUseCase deleteProjectorUseCase) {
        this.createProjectorUseCase = createProjectorUseCase;
        this.listProjectorsUseCase = listProjectorsUseCase;
        this.getProjectorByIdUseCase = getProjectorByIdUseCase;
        this.updateProjectorUseCase = updateProjectorUseCase;
        this.deleteProjectorUseCase = deleteProjectorUseCase;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','ATTENDANT')")
    public ResponseEntity<ProjectorResponse> create(@Valid @RequestBody CreateProjectorRequest request) {
        var created = createProjectorUseCase.execute(ProjectorMapper.fromCreateRequest(request));
        URI location = URI.create("/api/projectors/" + created.getId());
        return ResponseEntity.created(location).body(ProjectorMapper.toResponse(created));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ATTENDANT','PROFESSOR')")
    public ResponseEntity<List<ProjectorResponse>> list() {
        List<ProjectorResponse> projectors = listProjectorsUseCase.execute().stream()
                .map(ProjectorMapper::toResponse)
                .toList();
        return ResponseEntity.ok(projectors);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ATTENDANT','PROFESSOR')")
    public ResponseEntity<ProjectorResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ProjectorMapper.toResponse(getProjectorByIdUseCase.execute(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ATTENDANT')")
    public ResponseEntity<ProjectorResponse> update(
            @PathVariable Long id, @Valid @RequestBody UpdateProjectorRequest request) {
        return ResponseEntity.ok(ProjectorMapper.toResponse(
                updateProjectorUseCase.execute(id, ProjectorMapper.fromUpdateRequest(request))));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteProjectorUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
