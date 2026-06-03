package com.gac.api.application.service.key;

import org.springframework.stereotype.Service;

import com.gac.api.domain.model.Key;
import com.gac.api.domain.exception.BusinessRuleException;
import com.gac.api.domain.exception.NotFoundException;
import com.gac.api.application.repository.KeyRepository;

@Service
public class UpdateKeyService {

    private final KeyRepository keyRepository;

    public UpdateKeyService(KeyRepository keyRepository) {
        this.keyRepository = keyRepository;
    }

    public Key execute(Long id, Key updatedData) {
        Key existing = keyRepository.findById(id)
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

        return keyRepository.save(existing);
    }
}
