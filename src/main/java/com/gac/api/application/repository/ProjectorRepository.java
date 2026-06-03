package com.gac.api.application.repository;

import com.gac.api.domain.model.Projector;
import java.util.List;
import java.util.Optional;

public interface ProjectorRepository {

    Projector save(Projector projector);

    List<Projector> findAll();

    Optional<Projector> findById(Long id);

    Optional<Projector> findByAssetTag(String assetTag);

    void deleteById(Long id);
}
