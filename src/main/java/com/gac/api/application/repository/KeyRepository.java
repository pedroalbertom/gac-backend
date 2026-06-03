package com.gac.api.application.repository;

import com.gac.api.domain.model.Key;
import java.util.List;
import java.util.Optional;

public interface KeyRepository {

    Key save(Key key);

    List<Key> findAll();

    Optional<Key> findById(Long id);

    void deleteById(Long id);
}
