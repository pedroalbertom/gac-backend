package com.gac.api.presentation.mapper;

import com.gac.api.core.domain.Key;
import com.gac.api.presentation.dto.request.CreateKeyRequest;
import com.gac.api.presentation.dto.request.UpdateKeyRequest;
import com.gac.api.presentation.dto.response.KeyResponse;

public final class KeyMapper {

    private KeyMapper() {
    }

    public static KeyResponse toResponse(Key key) {
        return new KeyResponse(
                key.getId(),
                key.getRoom(),
                key.getBlock(),
                key.getAssetTag(),
                key.isSpareKey(),
                key.getStatus(),
                key.getReservedRegistrationNumber(),
                key.getDefectDescription());
    }

    public static Key fromCreateRequest(CreateKeyRequest request) {
        Key key = new Key();
        key.setRoom(request.room());
        key.setBlock(request.block());
        key.setAssetTag(request.assetTag());
        key.setSpareKey(request.spareKey());
        return key;
    }

    public static Key fromUpdateRequest(UpdateKeyRequest request) {
        Key key = new Key();
        key.setRoom(request.room());
        key.setBlock(request.block());
        key.setAssetTag(request.assetTag());
        key.setSpareKey(request.spareKey());
        return key;
    }
}
