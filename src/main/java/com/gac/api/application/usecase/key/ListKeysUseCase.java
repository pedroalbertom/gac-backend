package com.gac.api.application.usecase.key;

import com.gac.api.application.port.in.key.ListKeysInputPort;

import com.gac.api.domain.model.Key;
import com.gac.api.application.port.out.KeyGateway;
import java.util.List;

public class ListKeysUseCase implements ListKeysInputPort {

    private final KeyGateway keyGateway;

    public ListKeysUseCase(KeyGateway keyGateway) {
        this.keyGateway = keyGateway;
    }

    public List<Key> execute() {
        return keyGateway.findAll();
    }
}
