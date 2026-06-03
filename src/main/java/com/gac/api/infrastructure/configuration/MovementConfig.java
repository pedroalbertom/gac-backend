package com.gac.api.infrastructure.configuration;

import com.gac.api.core.gateway.KeyGateway;
import com.gac.api.core.gateway.MovementGateway;
import com.gac.api.core.gateway.ProjectorGateway;
import com.gac.api.core.usecase.movement.CancelReservationUseCase;
import com.gac.api.core.usecase.movement.ConfirmLoanUseCase;
import com.gac.api.core.usecase.movement.CreateReservationUseCase;
import com.gac.api.core.usecase.movement.FindMovementsByProfessorUseCase;
import com.gac.api.core.usecase.movement.ListActiveLoansUseCase;
import com.gac.api.core.usecase.movement.ListOpenReservationsUseCase;
import com.gac.api.core.usecase.movement.ExchangeAssetUseCase;
import com.gac.api.core.usecase.movement.ExpireReservationsUseCase;
import com.gac.api.core.usecase.movement.GenerateMovementReportUseCase;
import com.gac.api.core.usecase.movement.FindProfessorPendenciesUseCase;
import com.gac.api.core.usecase.movement.RegisterReturnUseCase;
import com.gac.api.core.usecase.movement.ReleaseFromMaintenanceUseCase;
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
    public FindMovementsByProfessorUseCase findMovementsByProfessorUseCase(MovementGateway gateway) {
        return new FindMovementsByProfessorUseCase(gateway);
    }

    @Bean
    public RegisterReturnUseCase registerReturnUseCase(
            MovementGateway movementGateway, ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        return new RegisterReturnUseCase(movementGateway, projectorGateway, keyGateway);
    }

    @Bean
    public ListActiveLoansUseCase listActiveLoansUseCase(MovementGateway gateway) {
        return new ListActiveLoansUseCase(gateway);
    }

    @Bean
    public ListOpenReservationsUseCase listOpenReservationsUseCase(MovementGateway gateway) {
        return new ListOpenReservationsUseCase(gateway);
    }

    @Bean
    public ExchangeAssetUseCase exchangeAssetUseCase(
            MovementGateway movementGateway, ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        return new ExchangeAssetUseCase(movementGateway, projectorGateway, keyGateway);
    }

    @Bean
    public FindProfessorPendenciesUseCase findProfessorPendenciesUseCase(MovementGateway gateway) {
        return new FindProfessorPendenciesUseCase(gateway);
    }

    @Bean
    public ReleaseFromMaintenanceUseCase releaseFromMaintenanceUseCase(
            ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        return new ReleaseFromMaintenanceUseCase(projectorGateway, keyGateway);
    }

    @Bean
    public GenerateMovementReportUseCase generateMovementReportUseCase(MovementGateway movementGateway) {
        return new GenerateMovementReportUseCase(movementGateway);
    }

    @Bean
    public ExpireReservationsUseCase expireReservationsUseCase(
            MovementGateway movementGateway, ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        return new ExpireReservationsUseCase(movementGateway, projectorGateway, keyGateway);
    }
}
