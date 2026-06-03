package com.gac.api.infrastructure.persistence.key;

import com.gac.api.domain.model.ItemStatus;
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
@Table(name = "room_keys")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String room;
    private String block;

    private String assetTag;

    private boolean spareKey;

    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    private String reservedRegistrationNumber;
    private String defectDescription;
}
