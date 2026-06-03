package com.gac.api.core.gateway;

import com.gac.api.core.domain.Key;
import java.util.List;
import java.util.Optional;

public interface KeyGateway {

    Key save(Key key);

    List<Key> findAll();

    Optional<Key> findById(Long id);

    void deleteById(Long id);
}
