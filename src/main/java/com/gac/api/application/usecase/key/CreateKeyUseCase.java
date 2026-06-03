package com.gac.api.application.usecase.key;

import com.gac.api.application.port.in.key.CreateKeyInputPort;

import com.gac.api.domain.model.ItemStatus;
import com.gac.api.domain.model.Key;
import com.gac.api.domain.exception.BusinessRuleException;
import com.gac.api.application.port.out.KeyGateway;

public class CreateKeyUseCase implements CreateKeyInputPort {

    private final KeyGateway keyGateway;

    public CreateKeyUseCase(KeyGateway keyGateway) {
        this.keyGateway = keyGateway;
    }

    public Key execute(Key newKey) {
        if (newKey.getRoom() == null || newKey.getBlock() == null) {
            throw new BusinessRuleException("Room and block are required to register a key.");
        }

        newKey.setStatus(ItemStatus.AVAILABLE);
        return keyGateway.save(newKey);
    }
}
