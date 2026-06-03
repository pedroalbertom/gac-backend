package com.gac.api.infrastructure.persistence.key;

import com.gac.api.core.domain.Key;
import com.gac.api.core.gateway.KeyGateway;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class KeyGatewayImpl implements KeyGateway {

    private final KeyRepository repository;

    public KeyGatewayImpl(KeyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Key save(Key key) {
        return toDomain(repository.save(toEntity(key)));
    }

    @Override
    public List<Key> findAll() {
        return repository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<Key> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private KeyEntity toEntity(Key key) {
        return new KeyEntity(
                key.getId(),
                key.getRoom(),
                key.getBlock(),
                key.getAssetTag(),
                key.isSpareKey(),
                key.getStatus(),
                key.getReservedRegistrationNumber(),
                key.getDefectDescription());
    }

    private Key toDomain(KeyEntity entity) {
        return new Key(
                entity.getId(),
                entity.getRoom(),
                entity.getBlock(),
                entity.getAssetTag(),
                entity.isSpareKey(),
                entity.getStatus(),
                entity.getReservedRegistrationNumber(),
                entity.getDefectDescription());
    }
}
