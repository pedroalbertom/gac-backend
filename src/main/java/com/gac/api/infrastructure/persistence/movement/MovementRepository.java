package com.gac.api.infrastructure.persistence.movement;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementRepository extends JpaRepository<MovementEntity, Long> {

    List<MovementEntity> findByProfessorRegistrationNumber(String professorRegistrationNumber);
}
