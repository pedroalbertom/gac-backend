package com.gac.api.adapter.out.persistence.user;

import com.gac.api.domain.model.User;
import com.gac.api.domain.port.UserGateway;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UserGatewayImpl implements UserGateway {

    private final UserRepository repository;

    public UserGatewayImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        UserEntity entity = new UserEntity(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRegistrationNumber(),
                user.getPassword(),
                user.getRole());
        UserEntity saved = repository.save(entity);
        return toDomain(saved);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public Optional<User> findByRegistrationNumber(String registrationNumber) {
        return repository.findByRegistrationNumber(registrationNumber).map(this::toDomain);
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getRegistrationNumber(),
                entity.getPassword(),
                entity.getRole());
    }
}
