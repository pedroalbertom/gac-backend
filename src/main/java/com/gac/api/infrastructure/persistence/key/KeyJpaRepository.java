package com.gac.api.infrastructure.persistence.key;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyJpaRepository extends JpaRepository<KeyEntity, Long> {
}
