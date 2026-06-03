package com.gac.api.core.usecase.movement;

import com.gac.api.core.domain.ItemStatus;
import com.gac.api.core.domain.Key;
import com.gac.api.core.domain.Movement;
import com.gac.api.core.domain.MovementType;
import com.gac.api.core.domain.Projector;
import com.gac.api.core.domain.User;
import com.gac.api.core.gateway.KeyGateway;
import com.gac.api.core.gateway.MovementGateway;
import com.gac.api.core.gateway.ProjectorGateway;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CreateMovementUseCase {

    private final MovementGateway movementGateway;
    private final ProjectorGateway projectorGateway;
    private final KeyGateway keyGateway;

    public CreateMovementUseCase(
            MovementGateway movementGateway, ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        this.movementGateway = movementGateway;
        this.projectorGateway = projectorGateway;
        this.keyGateway = keyGateway;
    }

    public Movement execute(
            String professorRegistrationNumber,
            String room,
            List<Long> projectorIds,
            List<Long> keyIds,
            User attendant,
            MovementType type) {
        List<Projector> projectors = new ArrayList<>();
        List<Key> keys = new ArrayList<>();

        if (projectorIds == null) {
            projectorIds = List.of();
        }
        if (keyIds == null) {
            keyIds = List.of();
        }

        for (Long id : projectorIds) {
            Projector projector = projectorGateway.findById(id)
                    .orElseThrow(() -> new RuntimeException("Projector id " + id + " not found."));
            validateAndUpdateStatus(projector, type);
            projectorGateway.save(projector);
            projectors.add(projector);
        }

        for (Long id : keyIds) {
            Key key = keyGateway.findById(id)
                    .orElseThrow(() -> new RuntimeException("Key id " + id + " not found."));
            validateAndUpdateStatus(key, type);
            keyGateway.save(key);
            keys.add(key);
        }

        Movement movement = new Movement(
                null,
                LocalDateTime.now(),
                professorRegistrationNumber,
                room,
                attendant,
                type,
                projectors,
                keys);

        return movementGateway.save(movement);
    }

    private void validateAndUpdateStatus(Object item, MovementType type) {
        ItemStatus newStatus = (type == MovementType.LOAN) ? ItemStatus.ON_LOAN : ItemStatus.AVAILABLE;

        if (item instanceof Projector projector) {
            if (type == MovementType.LOAN && projector.getStatus() == ItemStatus.ON_LOAN) {
                throw new RuntimeException("Projector " + projector.getAssetTag() + " is already on loan.");
            }
            projector.setStatus(newStatus);
        } else if (item instanceof Key key) {
            if (type == MovementType.LOAN && key.getStatus() == ItemStatus.ON_LOAN) {
                throw new RuntimeException("Key for room " + key.getRoom() + " is already on loan.");
            }
            key.setStatus(newStatus);
        }
    }
}
