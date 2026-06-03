package com.gac.api.application.service.key;

import org.springframework.stereotype.Service;

import com.gac.api.domain.model.Key;
import com.gac.api.application.repository.KeyRepository;
import java.util.List;

@Service
public class ListKeysService {

    private final KeyRepository keyRepository;

    public ListKeysService(KeyRepository keyRepository) {
        this.keyRepository = keyRepository;
    }

    public List<Key> execute() {
        return keyRepository.findAll();
    }
}
