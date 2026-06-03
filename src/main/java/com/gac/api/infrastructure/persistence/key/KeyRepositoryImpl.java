package com.gac.api.infrastructure.persistence.key;

import com.gac.api.domain.model.Key;
import com.gac.api.application.repository.KeyRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class KeyRepositoryImpl implements KeyRepository {

    private final KeyJpaRepository jpaRepository;

    public KeyRepositoryImpl(KeyJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Key save(Key key) {
        return toDomain(jpaRepository.save(toEntity(key)));
    }

    @Override
    public List<Key> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<Key> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
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
