package com.gac.api.presentation.controller;

import com.gac.api.core.domain.AssetType;
import com.gac.api.core.domain.ItemStatus;
import com.gac.api.core.domain.Key;
import com.gac.api.core.domain.Projector;
import com.gac.api.core.usecase.key.ListKeysUseCase;
import com.gac.api.core.usecase.projector.ListProjectorsUseCase;
import com.gac.api.presentation.dto.response.AssetItemResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    private final ListProjectorsUseCase listProjectorsUseCase;
    private final ListKeysUseCase listKeysUseCase;

    public AssetController(ListProjectorsUseCase listProjectorsUseCase, ListKeysUseCase listKeysUseCase) {
        this.listProjectorsUseCase = listProjectorsUseCase;
        this.listKeysUseCase = listKeysUseCase;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ATTENDANT','PROFESSOR')")
    public ResponseEntity<List<AssetItemResponse>> list(
            @RequestParam(required = false) ItemStatus status,
            @RequestParam(required = false) AssetType type) {
        List<AssetItemResponse> items = new ArrayList<>();

        if (type == null || type == AssetType.PROJECTOR) {
            for (Projector projector : listProjectorsUseCase.execute()) {
                if (status == null || projector.getStatus() == status) {
                    items.add(new AssetItemResponse(
                            AssetType.PROJECTOR,
                            projector.getId(),
                            projector.getStatus(),
                            projector.getAssetTag(),
                            projector.getReservedRegistrationNumber()));
                }
            }
        }

        if (type == null || type == AssetType.KEY) {
            for (Key key : listKeysUseCase.execute()) {
                if (status == null || key.getStatus() == status) {
                    String label = key.getBlock() + " - " + key.getRoom();
                    items.add(new AssetItemResponse(
                            AssetType.KEY,
                            key.getId(),
                            key.getStatus(),
                            label,
                            key.getReservedRegistrationNumber()));
                }
            }
        }

        items.sort(Comparator.comparing(AssetItemResponse::label));
        return ResponseEntity.ok(items);
    }
}
