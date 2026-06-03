package com.gac.api.application.usecase.key;

import com.gac.api.application.port.in.key.GetKeyByIdInputPort;

import com.gac.api.domain.model.Key;
import com.gac.api.domain.exception.NotFoundException;
import com.gac.api.domain.port.KeyGateway;

public class GetKeyByIdUseCase implements GetKeyByIdInputPort {

    private final KeyGateway keyGateway;

    public GetKeyByIdUseCase(KeyGateway keyGateway) {
        this.keyGateway = keyGateway;
    }

    public Key execute(Long id) {
        return keyGateway.findById(id).orElseThrow(() -> new NotFoundException("Key not found."));
    }
}
