package com.gac.api.infrastructure.persistence.projector;

import com.gac.api.core.domain.ItemStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "projectors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;

    @Column(unique = true)
    private String assetTag;

    @Enumerated(EnumType.STRING)
    private ItemStatus status;
}
