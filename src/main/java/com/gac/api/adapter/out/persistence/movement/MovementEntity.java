package com.gac.api.adapter.out.persistence.movement;

import com.gac.api.domain.model.AssetType;
import com.gac.api.domain.model.MovementStatus;
import com.gac.api.domain.model.MovementType;
import com.gac.api.adapter.out.persistence.user.UserEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MovementType type;

    @Enumerated(EnumType.STRING)
    private MovementStatus status;

    private String professorRegistrationNumber;

    @ManyToOne
    @JoinColumn(name = "attendant_id")
    private UserEntity attendant;

    @Enumerated(EnumType.STRING)
    private AssetType assetType;

    private Long assetId;

    private String confirmationCode;
    private String academicPurpose;
    private String room;
    private String defectDescription;

    private LocalDateTime checkedOutAt;
    private LocalDateTime returnedAt;
    private LocalDateTime createdAt;

    @ElementCollection
    @CollectionTable(name = "movement_loaned_accessories", joinColumns = @JoinColumn(name = "movement_id"))
    @Column(name = "accessory")
    private List<String> loanedAccessories = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "movement_returned_accessories", joinColumns = @JoinColumn(name = "movement_id"))
    @Column(name = "accessory")
    private List<String> returnedAccessories = new ArrayList<>();
}
