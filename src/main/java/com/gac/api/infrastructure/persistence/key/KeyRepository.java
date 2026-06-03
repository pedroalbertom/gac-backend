package com.gac.api.infrastructure.persistence.key;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyRepository extends JpaRepository<KeyEntity, Long> {
}
