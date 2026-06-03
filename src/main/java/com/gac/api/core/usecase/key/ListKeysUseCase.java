package com.gac.api.core.usecase.key;

import com.gac.api.core.domain.Key;
import com.gac.api.core.gateway.KeyGateway;
import java.util.List;

public class ListKeysUseCase {

    private final KeyGateway keyGateway;

    public ListKeysUseCase(KeyGateway keyGateway) {
        this.keyGateway = keyGateway;
    }

    public List<Key> execute() {
        return keyGateway.findAll();
    }
}
