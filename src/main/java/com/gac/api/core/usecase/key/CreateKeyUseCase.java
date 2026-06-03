package com.gac.api.core.usecase.key;

import com.gac.api.core.domain.ItemStatus;
import com.gac.api.core.domain.Key;
import com.gac.api.core.gateway.KeyGateway;

public class CreateKeyUseCase {

    private final KeyGateway keyGateway;

    public CreateKeyUseCase(KeyGateway keyGateway) {
        this.keyGateway = keyGateway;
    }

    public Key execute(Key newKey) {
        if (newKey.getRoom() == null || newKey.getBlock() == null) {
            throw new RuntimeException("Room and block are required to register a key.");
        }

        newKey.setStatus(ItemStatus.AVAILABLE);
        return keyGateway.save(newKey);
    }
}
