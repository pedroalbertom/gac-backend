package com.gac.api.presentation.controller;

import com.gac.api.application.service.asset.SearchAssetsService;
import com.gac.api.application.service.key.ListKeysService;
import com.gac.api.application.service.movement.ReleaseFromMaintenanceService;
import com.gac.api.application.service.projector.ListProjectorsService;

import com.gac.api.domain.model.AssetType;
import com.gac.api.domain.model.ItemStatus;
import com.gac.api.domain.model.Key;
import com.gac.api.domain.model.Projector;
import com.gac.api.presentation.dto.response.AssetItemResponse;
import com.gac.api.presentation.mapper.PendencyMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assets")
@Tag(name = "Assets", description = "Unified inventory, search, and maintenance release (UC07, UC09, UC15)")
public class AssetController {

    private final ListProjectorsService listProjectorsUseCase;
    private final ListKeysService listKeysUseCase;
    private final SearchAssetsService searchAssetsUseCase;
    private final ReleaseFromMaintenanceService releaseFromMaintenanceUseCase;

    public AssetController(
            ListProjectorsService listProjectorsUseCase,
            ListKeysService listKeysUseCase,
            SearchAssetsService searchAssetsUseCase,
            ReleaseFromMaintenanceService releaseFromMaintenanceUseCase) {
        this.listProjectorsUseCase = listProjectorsUseCase;
        this.listKeysUseCase = listKeysUseCase;
        this.searchAssetsUseCase = searchAssetsUseCase;
        this.releaseFromMaintenanceUseCase = releaseFromMaintenanceUseCase;
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

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','ATTENDANT','PROFESSOR')")
    public ResponseEntity<List<AssetItemResponse>> search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) ItemStatus status,
            @RequestParam(required = false) AssetType type,
            @RequestParam(required = false) Boolean spareKey) {
        List<AssetItemResponse> results = searchAssetsUseCase.execute(q, status, type, spareKey).stream()
                .map(PendencyMapper::toAssetResponse)
                .toList();
        return ResponseEntity.ok(results);
    }

    @PatchMapping("/{assetType}/{id}/release-maintenance")
    @PreAuthorize("hasAnyRole('ADMIN','ATTENDANT')")
    public ResponseEntity<Void> releaseFromMaintenance(@PathVariable AssetType assetType, @PathVariable Long id) {
        releaseFromMaintenanceUseCase.execute(assetType, id);
        return ResponseEntity.noContent().build();
    }
}
