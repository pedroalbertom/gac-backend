package com.gac.api.presentation.controller;

import com.gac.api.core.usecase.key.CreateKeyUseCase;
import com.gac.api.core.usecase.key.DeleteKeyUseCase;
import com.gac.api.core.usecase.key.GetKeyByIdUseCase;
import com.gac.api.core.usecase.key.ListKeysUseCase;
import com.gac.api.core.usecase.key.UpdateKeyUseCase;
import com.gac.api.presentation.dto.request.CreateKeyRequest;
import com.gac.api.presentation.dto.request.UpdateKeyRequest;
import com.gac.api.presentation.dto.response.KeyResponse;
import com.gac.api.presentation.mapper.KeyMapper;
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
@RequestMapping("/api/keys")
public class KeyController {

    private final CreateKeyUseCase createKeyUseCase;
    private final ListKeysUseCase listKeysUseCase;
    private final GetKeyByIdUseCase getKeyByIdUseCase;
    private final UpdateKeyUseCase updateKeyUseCase;
    private final DeleteKeyUseCase deleteKeyUseCase;

    public KeyController(
            CreateKeyUseCase createKeyUseCase,
            ListKeysUseCase listKeysUseCase,
            GetKeyByIdUseCase getKeyByIdUseCase,
            UpdateKeyUseCase updateKeyUseCase,
            DeleteKeyUseCase deleteKeyUseCase) {
        this.createKeyUseCase = createKeyUseCase;
        this.listKeysUseCase = listKeysUseCase;
        this.getKeyByIdUseCase = getKeyByIdUseCase;
        this.updateKeyUseCase = updateKeyUseCase;
        this.deleteKeyUseCase = deleteKeyUseCase;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','ATTENDANT')")
    public ResponseEntity<KeyResponse> create(@Valid @RequestBody CreateKeyRequest request) {
        var created = createKeyUseCase.execute(KeyMapper.fromCreateRequest(request));
        URI location = URI.create("/api/keys/" + created.getId());
        return ResponseEntity.created(location).body(KeyMapper.toResponse(created));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ATTENDANT')")
    public ResponseEntity<List<KeyResponse>> list() {
        List<KeyResponse> keys =
                listKeysUseCase.execute().stream().map(KeyMapper::toResponse).toList();
        return ResponseEntity.ok(keys);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ATTENDANT')")
    public ResponseEntity<KeyResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(KeyMapper.toResponse(getKeyByIdUseCase.execute(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ATTENDANT')")
    public ResponseEntity<KeyResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateKeyRequest request) {
        return ResponseEntity.ok(
                KeyMapper.toResponse(updateKeyUseCase.execute(id, KeyMapper.fromUpdateRequest(request))));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteKeyUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
