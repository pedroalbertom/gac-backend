package com.gac.api.application.service.key;

import org.springframework.stereotype.Service;

import com.gac.api.domain.model.ItemStatus;
import com.gac.api.domain.model.Key;
import com.gac.api.domain.exception.BusinessRuleException;
import com.gac.api.domain.exception.NotFoundException;
import com.gac.api.application.repository.KeyRepository;

@Service
public class DeleteKeyService {

    private final KeyRepository keyRepository;

    public DeleteKeyService(KeyRepository keyRepository) {
        this.keyRepository = keyRepository;
    }

    public void execute(Long id) {
        Key key = keyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Key not found."));

        if (key.getStatus() == ItemStatus.ON_LOAN || key.getStatus() == ItemStatus.RESERVED) {
            throw new BusinessRuleException("Reserved or on-loan keys cannot be removed.");
        }

        keyRepository.deleteById(id);
    }
}
