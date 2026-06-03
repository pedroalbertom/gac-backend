package com.gac.api.domain.port;

import com.gac.api.domain.model.Projector;
import java.util.List;
import java.util.Optional;

public interface ProjectorGateway {

    Projector save(Projector projector);

    List<Projector> findAll();

    Optional<Projector> findById(Long id);

    Optional<Projector> findByAssetTag(String assetTag);

    void deleteById(Long id);
}
