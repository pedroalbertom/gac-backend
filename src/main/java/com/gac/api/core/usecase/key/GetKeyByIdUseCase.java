package com.gac.api.core.usecase.key;

import com.gac.api.core.domain.Key;
import com.gac.api.core.exception.NotFoundException;
import com.gac.api.core.gateway.KeyGateway;

public class GetKeyByIdUseCase {

    private final KeyGateway keyGateway;

    public GetKeyByIdUseCase(KeyGateway keyGateway) {
        this.keyGateway = keyGateway;
    }

    public Key execute(Long id) {
        return keyGateway.findById(id).orElseThrow(() -> new NotFoundException("Key not found."));
    }
}
