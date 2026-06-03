package com.gac.api.presentation.controller;

import com.gac.api.application.service.key.CreateKeyService;
import com.gac.api.application.service.key.DeleteKeyService;
import com.gac.api.application.service.key.GetKeyByIdService;
import com.gac.api.application.service.key.ListKeysService;
import com.gac.api.application.service.key.UpdateKeyService;

import com.gac.api.presentation.dto.request.CreateKeyRequest;
import com.gac.api.presentation.dto.request.UpdateKeyRequest;
import com.gac.api.presentation.dto.response.KeyResponse;
import com.gac.api.presentation.mapper.KeyMapper;
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
@RequestMapping("/api/keys")
@Tag(name = "Keys", description = "Key CRUD and spare keys (UC08, UC10, UC13, UC14)")
public class KeyController {

    private final CreateKeyService createKeyUseCase;
    private final ListKeysService listKeysUseCase;
    private final GetKeyByIdService getKeyByIdUseCase;
    private final UpdateKeyService updateKeyUseCase;
    private final DeleteKeyService deleteKeyUseCase;

    public KeyController(
            CreateKeyService createKeyUseCase,
            ListKeysService listKeysUseCase,
            GetKeyByIdService getKeyByIdUseCase,
            UpdateKeyService updateKeyUseCase,
            DeleteKeyService deleteKeyUseCase) {
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
    @PreAuthorize("hasAnyRole('ADMIN','ATTENDANT','PROFESSOR')")
    public ResponseEntity<List<KeyResponse>> list() {
        List<KeyResponse> keys =
                listKeysUseCase.execute().stream().map(KeyMapper::toResponse).toList();
        return ResponseEntity.ok(keys);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ATTENDANT','PROFESSOR')")
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
