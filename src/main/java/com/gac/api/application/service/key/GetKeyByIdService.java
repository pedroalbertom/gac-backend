package com.gac.api.application.service.key;

import org.springframework.stereotype.Service;

import com.gac.api.domain.model.Key;
import com.gac.api.domain.exception.NotFoundException;
import com.gac.api.application.repository.KeyRepository;

@Service
public class GetKeyByIdService {

    private final KeyRepository keyRepository;

    public GetKeyByIdService(KeyRepository keyRepository) {
        this.keyRepository = keyRepository;
    }

    public Key execute(Long id) {
        return keyRepository.findById(id).orElseThrow(() -> new NotFoundException("Key not found."));
    }
}
