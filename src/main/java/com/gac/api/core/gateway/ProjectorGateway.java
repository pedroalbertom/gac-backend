package com.gac.api.core.gateway;

import com.gac.api.core.domain.Projector;
import java.util.List;
import java.util.Optional;

public interface ProjectorGateway {

    Projector save(Projector projector);

    List<Projector> findAll();

    Optional<Projector> findById(Long id);

    Optional<Projector> findByAssetTag(String assetTag);

    void deleteById(Long id);
}
