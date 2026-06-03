package com.gac.api.application.port.in.asset;

import com.gac.api.domain.model.AssetSummary;
import com.gac.api.domain.model.AssetType;
import com.gac.api.domain.model.ItemStatus;
import java.util.List;

public interface SearchAssetsInputPort {
    List<AssetSummary> execute(String query, ItemStatus status, AssetType type, Boolean spareKey);
}
