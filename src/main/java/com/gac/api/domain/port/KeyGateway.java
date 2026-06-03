package com.gac.api.domain.port;

import com.gac.api.domain.model.Key;
import java.util.List;
import java.util.Optional;

public interface KeyGateway {

    Key save(Key key);

    List<Key> findAll();

    Optional<Key> findById(Long id);

    void deleteById(Long id);
}
