package com.gac.api.core.usecase.movement;

import com.gac.api.core.domain.AssetType;
import com.gac.api.core.domain.ItemStatus;
import com.gac.api.core.domain.Key;
import com.gac.api.core.domain.Projector;
import com.gac.api.core.gateway.KeyGateway;
import com.gac.api.core.gateway.ProjectorGateway;

final class AssetInventory {

    private AssetInventory() {
    }

    static void requireAvailable(AssetType assetType, Long assetId, ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        ItemStatus status = getStatus(assetType, assetId, projectorGateway, keyGateway);
        if (status != ItemStatus.AVAILABLE) {
            throw new RuntimeException("Asset must be available to reserve.");
        }
    }

    static void requireReservedForProfessor(
            AssetType assetType,
            Long assetId,
            String professorRegistrationNumber,
            ProjectorGateway projectorGateway,
            KeyGateway keyGateway) {
        if (assetType == AssetType.PROJECTOR) {
            Projector projector = projectorGateway
                    .findById(assetId)
                    .orElseThrow(() -> new RuntimeException("Projector not found."));
            if (projector.getStatus() != ItemStatus.RESERVED
                    || !professorRegistrationNumber.equals(projector.getReservedRegistrationNumber())) {
                throw new RuntimeException("Asset is not reserved for this professor.");
            }
            return;
        }

        Key key = keyGateway.findById(assetId).orElseThrow(() -> new RuntimeException("Key not found."));
        if (key.getStatus() != ItemStatus.RESERVED
                || !professorRegistrationNumber.equals(key.getReservedRegistrationNumber())) {
            throw new RuntimeException("Asset is not reserved for this professor.");
        }
    }

    static void markReserved(
            AssetType assetType,
            Long assetId,
            String professorRegistrationNumber,
            ProjectorGateway projectorGateway,
            KeyGateway keyGateway) {
        if (assetType == AssetType.PROJECTOR) {
            Projector projector = projectorGateway
                    .findById(assetId)
                    .orElseThrow(() -> new RuntimeException("Projector not found."));
            projector.setStatus(ItemStatus.RESERVED);
            projector.setReservedRegistrationNumber(professorRegistrationNumber);
            projectorGateway.save(projector);
            return;
        }

        Key key = keyGateway.findById(assetId).orElseThrow(() -> new RuntimeException("Key not found."));
        key.setStatus(ItemStatus.RESERVED);
        key.setReservedRegistrationNumber(professorRegistrationNumber);
        keyGateway.save(key);
    }

    static void markOnLoan(AssetType assetType, Long assetId, ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        if (assetType == AssetType.PROJECTOR) {
            Projector projector = projectorGateway
                    .findById(assetId)
                    .orElseThrow(() -> new RuntimeException("Projector not found."));
            projector.setStatus(ItemStatus.ON_LOAN);
            projector.setReservedRegistrationNumber(null);
            projectorGateway.save(projector);
            return;
        }

        Key key = keyGateway.findById(assetId).orElseThrow(() -> new RuntimeException("Key not found."));
        key.setStatus(ItemStatus.ON_LOAN);
        key.setReservedRegistrationNumber(null);
        keyGateway.save(key);
    }

    static void markMaintenance(
            AssetType assetType,
            Long assetId,
            String defectDescription,
            ProjectorGateway projectorGateway,
            KeyGateway keyGateway) {
        if (assetType == AssetType.PROJECTOR) {
            Projector projector = projectorGateway
                    .findById(assetId)
                    .orElseThrow(() -> new RuntimeException("Projector not found."));
            projector.setStatus(ItemStatus.MAINTENANCE);
            projector.setReservedRegistrationNumber(null);
            projector.setDefectDescription(defectDescription);
            projectorGateway.save(projector);
            return;
        }

        Key key = keyGateway.findById(assetId).orElseThrow(() -> new RuntimeException("Key not found."));
        key.setStatus(ItemStatus.MAINTENANCE);
        key.setReservedRegistrationNumber(null);
        key.setDefectDescription(defectDescription);
        keyGateway.save(key);
    }

    static void markAvailable(AssetType assetType, Long assetId, ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        if (assetType == AssetType.PROJECTOR) {
            Projector projector = projectorGateway
                    .findById(assetId)
                    .orElseThrow(() -> new RuntimeException("Projector not found."));
            projector.setStatus(ItemStatus.AVAILABLE);
            projector.setReservedRegistrationNumber(null);
            projectorGateway.save(projector);
            return;
        }

        Key key = keyGateway.findById(assetId).orElseThrow(() -> new RuntimeException("Key not found."));
        key.setStatus(ItemStatus.AVAILABLE);
        key.setReservedRegistrationNumber(null);
        keyGateway.save(key);
    }

    private static ItemStatus getStatus(
            AssetType assetType, Long assetId, ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        if (assetType == AssetType.PROJECTOR) {
            return projectorGateway
                    .findById(assetId)
                    .orElseThrow(() -> new RuntimeException("Projector not found."))
                    .getStatus();
        }
        return keyGateway
                .findById(assetId)
                .orElseThrow(() -> new RuntimeException("Key not found."))
                .getStatus();
    }
}
