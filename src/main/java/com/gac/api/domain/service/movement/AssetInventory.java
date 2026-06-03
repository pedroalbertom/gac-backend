package com.gac.api.domain.service.movement;

import com.gac.api.domain.exception.BusinessRuleException;
import com.gac.api.domain.exception.NotFoundException;
import com.gac.api.domain.model.AssetType;
import com.gac.api.domain.model.ItemStatus;
import com.gac.api.domain.model.Key;
import com.gac.api.domain.model.Projector;
import com.gac.api.application.repository.KeyRepository;
import com.gac.api.application.repository.ProjectorRepository;

public final class AssetInventory {

    private AssetInventory() {
    }

    public static void requireAvailable(
            AssetType assetType, Long assetId, ProjectorRepository projectorGateway, KeyRepository keyGateway) {
        ItemStatus status = getStatus(assetType, assetId, projectorGateway, keyGateway);
        if (status != ItemStatus.AVAILABLE) {
            throw new BusinessRuleException("Asset must be available to reserve.");
        }
    }

    public static void requireReservedForProfessor(
            AssetType assetType,
            Long assetId,
            String professorRegistrationNumber,
            ProjectorRepository projectorGateway,
            KeyRepository keyGateway) {
        if (assetType == AssetType.PROJECTOR) {
            Projector projector = projectorGateway
                    .findById(assetId)
                    .orElseThrow(() -> new NotFoundException("Projector not found."));
            if (projector.getStatus() != ItemStatus.RESERVED
                    || !professorRegistrationNumber.equals(projector.getReservedRegistrationNumber())) {
                throw new BusinessRuleException("Asset is not reserved for this professor.");
            }
            return;
        }

        Key key = keyGateway.findById(assetId).orElseThrow(() -> new NotFoundException("Key not found."));
        if (key.getStatus() != ItemStatus.RESERVED
                || !professorRegistrationNumber.equals(key.getReservedRegistrationNumber())) {
            throw new BusinessRuleException("Asset is not reserved for this professor.");
        }
    }

    public static void markReserved(
            AssetType assetType,
            Long assetId,
            String professorRegistrationNumber,
            ProjectorRepository projectorGateway,
            KeyRepository keyGateway) {
        if (assetType == AssetType.PROJECTOR) {
            Projector projector = projectorGateway
                    .findById(assetId)
                    .orElseThrow(() -> new NotFoundException("Projector not found."));
            projector.setStatus(ItemStatus.RESERVED);
            projector.setReservedRegistrationNumber(professorRegistrationNumber);
            projectorGateway.save(projector);
            return;
        }

        Key key = keyGateway.findById(assetId).orElseThrow(() -> new NotFoundException("Key not found."));
        key.setStatus(ItemStatus.RESERVED);
        key.setReservedRegistrationNumber(professorRegistrationNumber);
        keyGateway.save(key);
    }

    public static void markOnLoan(
            AssetType assetType, Long assetId, ProjectorRepository projectorGateway, KeyRepository keyGateway) {
        if (assetType == AssetType.PROJECTOR) {
            Projector projector = projectorGateway
                    .findById(assetId)
                    .orElseThrow(() -> new NotFoundException("Projector not found."));
            projector.setStatus(ItemStatus.ON_LOAN);
            projector.setReservedRegistrationNumber(null);
            projectorGateway.save(projector);
            return;
        }

        Key key = keyGateway.findById(assetId).orElseThrow(() -> new NotFoundException("Key not found."));
        key.setStatus(ItemStatus.ON_LOAN);
        key.setReservedRegistrationNumber(null);
        keyGateway.save(key);
    }

    public static void markMaintenance(
            AssetType assetType,
            Long assetId,
            String defectDescription,
            ProjectorRepository projectorGateway,
            KeyRepository keyGateway) {
        if (assetType == AssetType.PROJECTOR) {
            Projector projector = projectorGateway
                    .findById(assetId)
                    .orElseThrow(() -> new NotFoundException("Projector not found."));
            projector.setStatus(ItemStatus.MAINTENANCE);
            projector.setReservedRegistrationNumber(null);
            projector.setDefectDescription(defectDescription);
            projectorGateway.save(projector);
            return;
        }

        Key key = keyGateway.findById(assetId).orElseThrow(() -> new NotFoundException("Key not found."));
        key.setStatus(ItemStatus.MAINTENANCE);
        key.setReservedRegistrationNumber(null);
        key.setDefectDescription(defectDescription);
        keyGateway.save(key);
    }

    public static void releaseFromMaintenance(
            AssetType assetType, Long assetId, ProjectorRepository projectorGateway, KeyRepository keyGateway) {
        if (assetType == AssetType.PROJECTOR) {
            Projector projector = projectorGateway
                    .findById(assetId)
                    .orElseThrow(() -> new NotFoundException("Projector not found."));
            if (projector.getStatus() != ItemStatus.MAINTENANCE) {
                throw new BusinessRuleException("Asset is not in maintenance.");
            }
            projector.setStatus(ItemStatus.AVAILABLE);
            projector.setReservedRegistrationNumber(null);
            projector.setDefectDescription(null);
            projectorGateway.save(projector);
            return;
        }

        Key key = keyGateway.findById(assetId).orElseThrow(() -> new NotFoundException("Key not found."));
        if (key.getStatus() != ItemStatus.MAINTENANCE) {
            throw new BusinessRuleException("Asset is not in maintenance.");
        }
        key.setStatus(ItemStatus.AVAILABLE);
        key.setReservedRegistrationNumber(null);
        key.setDefectDescription(null);
        keyGateway.save(key);
    }

    public static void markAvailable(
            AssetType assetType, Long assetId, ProjectorRepository projectorGateway, KeyRepository keyGateway) {
        if (assetType == AssetType.PROJECTOR) {
            Projector projector = projectorGateway
                    .findById(assetId)
                    .orElseThrow(() -> new NotFoundException("Projector not found."));
            projector.setStatus(ItemStatus.AVAILABLE);
            projector.setReservedRegistrationNumber(null);
            projector.setDefectDescription(null);
            projectorGateway.save(projector);
            return;
        }

        Key key = keyGateway.findById(assetId).orElseThrow(() -> new NotFoundException("Key not found."));
        key.setStatus(ItemStatus.AVAILABLE);
        key.setReservedRegistrationNumber(null);
        key.setDefectDescription(null);
        keyGateway.save(key);
    }

    private static ItemStatus getStatus(
            AssetType assetType, Long assetId, ProjectorRepository projectorGateway, KeyRepository keyGateway) {
        if (assetType == AssetType.PROJECTOR) {
            return projectorGateway
                    .findById(assetId)
                    .orElseThrow(() -> new NotFoundException("Projector not found."))
                    .getStatus();
        }
        return keyGateway
                .findById(assetId)
                .orElseThrow(() -> new NotFoundException("Key not found."))
                .getStatus();
    }
}
