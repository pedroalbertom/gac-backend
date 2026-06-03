package com.gac.api.application.usecase.key;

import com.gac.api.application.port.in.key.UpdateKeyInputPort;

import com.gac.api.domain.model.Key;
import com.gac.api.domain.exception.BusinessRuleException;
import com.gac.api.domain.exception.NotFoundException;
import com.gac.api.domain.port.KeyGateway;

public class UpdateKeyUseCase implements UpdateKeyInputPort {

    private final KeyGateway keyGateway;

    public UpdateKeyUseCase(KeyGateway keyGateway) {
        this.keyGateway = keyGateway;
    }

    public Key execute(Long id, Key updatedData) {
        Key existing = keyGateway.findById(id)
                .orElseThrow(() -> new NotFoundException("Key not found."));

        existing.setRoom(updatedData.getRoom());
        existing.setBlock(updatedData.getBlock());
        existing.setSpareKey(updatedData.isSpareKey());
        if (updatedData.getAssetTag() != null) {
            existing.setAssetTag(updatedData.getAssetTag());
        }

        if (existing.getRoom() == null || existing.getBlock() == null) {
            throw new BusinessRuleException("Room and block are required.");
        }

        return keyGateway.save(existing);
    }
}
