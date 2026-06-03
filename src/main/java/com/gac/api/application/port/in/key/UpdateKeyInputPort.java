package com.gac.api.application.port.in.key;

import com.gac.api.domain.model.Key;

public interface UpdateKeyInputPort {
    Key execute(Long id, Key updatedData);
}
