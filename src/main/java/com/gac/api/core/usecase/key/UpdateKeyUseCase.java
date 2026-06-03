package com.gac.api.core.usecase.key;

import com.gac.api.core.domain.Key;
import com.gac.api.core.gateway.KeyGateway;

public class UpdateKeyUseCase {

    private final KeyGateway keyGateway;

    public UpdateKeyUseCase(KeyGateway keyGateway) {
        this.keyGateway = keyGateway;
    }

    public Key execute(Long id, Key updatedData) {
        Key existing = keyGateway.findById(id)
                .orElseThrow(() -> new RuntimeException("Key not found for the given id."));

        existing.setRoom(updatedData.getRoom());
        existing.setBlock(updatedData.getBlock());
        existing.setSpareKey(updatedData.isSpareKey());
        if (updatedData.getAssetTag() != null) {
            existing.setAssetTag(updatedData.getAssetTag());
        }

        if (existing.getRoom() == null || existing.getBlock() == null) {
            throw new RuntimeException("Room and block are required.");
        }

        return keyGateway.save(existing);
    }
}
