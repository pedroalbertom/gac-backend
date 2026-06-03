package com.gac.api.infrastructure.persistence.movement;

import com.gac.api.core.domain.MovementType;
import com.gac.api.infrastructure.persistence.key.KeyEntity;
import com.gac.api.infrastructure.persistence.projector.ProjectorEntity;
import com.gac.api.infrastructure.persistence.user.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
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

    private LocalDateTime dateTime;
    private String professorRegistrationNumber;
    private String room;

    @ManyToOne
    @JoinColumn(name = "attendant_id")
    private UserEntity attendant;

    @Enumerated(EnumType.STRING)
    private MovementType type;

    @ManyToMany
    @JoinTable(
            name = "movement_projectors",
            joinColumns = @JoinColumn(name = "movement_id"),
            inverseJoinColumns = @JoinColumn(name = "projector_id"))
    private List<ProjectorEntity> projectors;

    @ManyToMany
    @JoinTable(
            name = "movement_room_keys",
            joinColumns = @JoinColumn(name = "movement_id"),
            inverseJoinColumns = @JoinColumn(name = "key_id"))
    private List<KeyEntity> keys;
}
