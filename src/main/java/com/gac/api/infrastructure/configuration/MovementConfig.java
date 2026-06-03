package com.gac.api.infrastructure.configuration;

import com.gac.api.core.gateway.KeyGateway;
import com.gac.api.core.gateway.MovementGateway;
import com.gac.api.core.gateway.ProjectorGateway;
import com.gac.api.core.usecase.movement.CreateMovementUseCase;
import com.gac.api.core.usecase.movement.DeleteMovementUseCase;
import com.gac.api.core.usecase.movement.FindMovementsByProfessorUseCase;
import com.gac.api.core.usecase.movement.ListMovementsUseCase;
import com.gac.api.core.usecase.movement.UpdateMovementUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MovementConfig {

    @Bean
    public CreateMovementUseCase createMovementUseCase(
            MovementGateway movementGateway, ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        return new CreateMovementUseCase(movementGateway, projectorGateway, keyGateway);
    }

    @Bean
    public ListMovementsUseCase listMovementsUseCase(MovementGateway gateway) {
        return new ListMovementsUseCase(gateway);
    }

    @Bean
    public UpdateMovementUseCase updateMovementUseCase(MovementGateway gateway) {
        return new UpdateMovementUseCase(gateway);
    }

    @Bean
    public DeleteMovementUseCase deleteMovementUseCase(MovementGateway gateway) {
        return new DeleteMovementUseCase(gateway);
    }

    @Bean
    public FindMovementsByProfessorUseCase findMovementsByProfessorUseCase(MovementGateway gateway) {
        return new FindMovementsByProfessorUseCase(gateway);
    }
}
