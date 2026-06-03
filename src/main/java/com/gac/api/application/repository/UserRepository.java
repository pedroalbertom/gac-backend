package com.gac.api.application.repository;

import com.gac.api.domain.model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    List<User> findAll();

    Optional<User> findByEmail(String email);

    Optional<User> findByRegistrationNumber(String registrationNumber);

    Optional<User> findById(Long id);

    void deleteById(Long id);
}
