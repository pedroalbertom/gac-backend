package com.gac.api.core.usecase.asset;

import com.gac.api.core.domain.AssetSummary;
import com.gac.api.core.domain.AssetType;
import com.gac.api.core.domain.ItemStatus;
import com.gac.api.core.domain.Key;
import com.gac.api.core.domain.Projector;
import com.gac.api.core.gateway.KeyGateway;
import com.gac.api.core.gateway.ProjectorGateway;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class SearchAssetsUseCase {

    private final ProjectorGateway projectorGateway;
    private final KeyGateway keyGateway;

    public SearchAssetsUseCase(ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        this.projectorGateway = projectorGateway;
        this.keyGateway = keyGateway;
    }

    public List<AssetSummary> execute(String query, ItemStatus status, AssetType type, Boolean spareKey) {
        String term = query != null ? query.trim().toLowerCase(Locale.ROOT) : "";
        List<AssetSummary> items = new ArrayList<>();

        if (type == null || type == AssetType.PROJECTOR) {
            for (Projector projector : projectorGateway.findAll()) {
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
            for (Key key : keyGateway.findAll()) {
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
