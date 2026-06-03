package com.gac.api.application.service.key;

import org.springframework.stereotype.Service;

import com.gac.api.domain.model.ItemStatus;
import com.gac.api.domain.model.Key;
import com.gac.api.domain.exception.BusinessRuleException;
import com.gac.api.application.repository.KeyRepository;

@Service
public class CreateKeyService {

    private final KeyRepository keyRepository;

    public CreateKeyService(KeyRepository keyRepository) {
        this.keyRepository = keyRepository;
    }

    public Key execute(Key newKey) {
        if (newKey.getRoom() == null || newKey.getBlock() == null) {
            throw new BusinessRuleException("Room and block are required to register a key.");
        }

        newKey.setStatus(ItemStatus.AVAILABLE);
        return keyRepository.save(newKey);
    }
}
