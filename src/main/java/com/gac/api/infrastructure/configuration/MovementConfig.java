package com.gac.api.infrastructure.configuration;

import com.gac.api.core.gateway.KeyGateway;
import com.gac.api.core.gateway.MovementGateway;
import com.gac.api.core.gateway.ProjectorGateway;
import com.gac.api.core.usecase.movement.CancelReservationUseCase;
import com.gac.api.core.usecase.movement.ConfirmLoanUseCase;
import com.gac.api.core.usecase.movement.CreateReservationUseCase;
import com.gac.api.core.usecase.movement.FindMovementsByProfessorUseCase;
import com.gac.api.core.usecase.movement.ListMovementsUseCase;
import com.gac.api.core.usecase.movement.UpdateMovementUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MovementConfig {

    @Bean
    public CreateReservationUseCase createReservationUseCase(
            MovementGateway movementGateway, ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        return new CreateReservationUseCase(movementGateway, projectorGateway, keyGateway);
    }

    @Bean
    public ConfirmLoanUseCase confirmLoanUseCase(
            MovementGateway movementGateway, ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        return new ConfirmLoanUseCase(movementGateway, projectorGateway, keyGateway);
    }

    @Bean
    public CancelReservationUseCase cancelReservationUseCase(
            MovementGateway movementGateway, ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        return new CancelReservationUseCase(movementGateway, projectorGateway, keyGateway);
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
    public FindMovementsByProfessorUseCase findMovementsByProfessorUseCase(MovementGateway gateway) {
        return new FindMovementsByProfessorUseCase(gateway);
    }
}
