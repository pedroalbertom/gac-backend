package com.gac.api.application.service.asset;

import org.springframework.stereotype.Service;

import com.gac.api.domain.model.AssetSummary;
import com.gac.api.domain.model.AssetType;
import com.gac.api.domain.model.ItemStatus;
import com.gac.api.domain.model.Key;
import com.gac.api.domain.model.Projector;
import com.gac.api.application.repository.KeyRepository;
import com.gac.api.application.repository.ProjectorRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
public class SearchAssetsService {

    private final ProjectorRepository projectorRepository;
    private final KeyRepository keyRepository;

    public SearchAssetsService(ProjectorRepository projectorRepository, KeyRepository keyRepository) {
        this.projectorRepository = projectorRepository;
        this.keyRepository = keyRepository;
    }

    public List<AssetSummary> execute(String query, ItemStatus status, AssetType type, Boolean spareKey) {
        String term = query != null ? query.trim().toLowerCase(Locale.ROOT) : "";
        List<AssetSummary> items = new ArrayList<>();

        if (type == null || type == AssetType.PROJECTOR) {
            for (Projector projector : projectorRepository.findAll()) {
                if (status != null && projector.getStatus() != status) {
                    continue;
                }
                if (!matchesProjector(projector, term)) {
                    continue;
                }
                items.add(new AssetSummary(
                        AssetType.PROJECTOR,
                        projector.getId(),
                        projector.getStatus(),
                        projector.getAssetTag(),
                        projector.getReservedRegistrationNumber()));
            }
        }

        if (type == null || type == AssetType.KEY) {
            for (Key key : keyRepository.findAll()) {
                if (status != null && key.getStatus() != status) {
                    continue;
                }
                if (spareKey != null && key.isSpareKey() != spareKey) {
                    continue;
                }
                if (!matchesKey(key, term)) {
                    continue;
                }
                String label = key.getBlock() + " - " + key.getRoom();
                items.add(new AssetSummary(
                        AssetType.KEY, key.getId(), key.getStatus(), label, key.getReservedRegistrationNumber()));
            }
        }

        items.sort(Comparator.comparing(AssetSummary::label));
        return items;
    }

    private boolean matchesProjector(Projector projector, String term) {
        if (term.isEmpty()) {
            return true;
        }
        return contains(projector.getAssetTag(), term)
                || contains(projector.getSerialNumber(), term)
                || contains(projector.getBrand(), term)
                || contains(projector.getModel(), term)
                || contains(projector.getStatus() != null ? projector.getStatus().name() : null, term);
    }

    private boolean matchesKey(Key key, String term) {
        if (term.isEmpty()) {
            return true;
        }
        return contains(key.getRoom(), term)
                || contains(key.getBlock(), term)
                || contains(key.getAssetTag(), term)
                || contains(key.getStatus() != null ? key.getStatus().name() : null, term)
                || (key.isSpareKey() && (term.contains("spare") || term.contains("reserva")));
    }

    private boolean contains(String value, String term) {
        return value != null && value.toLowerCase(Locale.ROOT).contains(term);
    }
}
