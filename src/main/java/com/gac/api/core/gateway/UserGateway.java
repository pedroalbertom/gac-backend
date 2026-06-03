package com.gac.api.core.gateway;

import com.gac.api.core.domain.User;
import java.util.List;
import java.util.Optional;

public interface UserGateway {

    User save(User user);

    List<User> findAll();

    Optional<User> findByEmail(String email);

    Optional<User> findByRegistrationNumber(String registrationNumber);

    Optional<User> findById(Long id);

    void deleteById(Long id);
}
