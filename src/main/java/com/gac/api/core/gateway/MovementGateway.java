package com.gac.api.core.gateway;

import com.gac.api.core.domain.Movement;
import java.util.List;
import java.util.Optional;

public interface MovementGateway {

    Movement save(Movement movement);

    List<Movement> findAll();

    Optional<Movement> findById(Long id);

    List<Movement> findByProfessorRegistrationNumber(String registrationNumber);

    void deleteById(Long id);
}
