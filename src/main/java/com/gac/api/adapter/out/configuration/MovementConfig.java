package com.gac.api.adapter.out.configuration;

import com.gac.api.application.port.out.KeyGateway;
import com.gac.api.application.port.out.MovementGateway;
import com.gac.api.application.port.out.ProjectorGateway;
import com.gac.api.application.port.in.movement.CancelReservationInputPort;
import com.gac.api.application.port.in.movement.ConfirmLoanInputPort;
import com.gac.api.application.port.in.movement.CreateReservationInputPort;
import com.gac.api.application.port.in.movement.FindMovementsByProfessorInputPort;
import com.gac.api.application.port.in.movement.ListActiveLoansInputPort;
import com.gac.api.application.port.in.movement.ListOpenReservationsInputPort;
import com.gac.api.application.port.in.movement.ExchangeAssetInputPort;
import com.gac.api.application.port.in.movement.ExpireReservationsInputPort;
import com.gac.api.application.port.in.movement.GenerateMovementReportInputPort;
import com.gac.api.application.port.in.movement.FindProfessorPendenciesInputPort;
import com.gac.api.application.port.in.movement.RegisterReturnInputPort;
import com.gac.api.application.port.in.movement.ReleaseFromMaintenanceInputPort;
import com.gac.api.application.usecase.movement.CancelReservationUseCase;
import com.gac.api.application.usecase.movement.ConfirmLoanUseCase;
import com.gac.api.application.usecase.movement.CreateReservationUseCase;
import com.gac.api.application.usecase.movement.ExchangeAssetUseCase;
import com.gac.api.application.usecase.movement.ExpireReservationsUseCase;
import com.gac.api.application.usecase.movement.FindMovementsByProfessorUseCase;
import com.gac.api.application.usecase.movement.FindProfessorPendenciesUseCase;
import com.gac.api.application.usecase.movement.GenerateMovementReportUseCase;
import com.gac.api.application.usecase.movement.ListActiveLoansUseCase;
import com.gac.api.application.usecase.movement.ListOpenReservationsUseCase;
import com.gac.api.application.usecase.movement.RegisterReturnUseCase;
import com.gac.api.application.usecase.movement.ReleaseFromMaintenanceUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MovementConfig {

    @Bean
    public CreateReservationInputPort createReservationUseCase(
            MovementGateway movementGateway, ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        return new CreateReservationUseCase(movementGateway, projectorGateway, keyGateway);
    }

    @Bean
    public ConfirmLoanInputPort confirmLoanUseCase(
            MovementGateway movementGateway, ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        return new ConfirmLoanUseCase(movementGateway, projectorGateway, keyGateway);
    }

    @Bean
    public CancelReservationInputPort cancelReservationUseCase(
            MovementGateway movementGateway, ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        return new CancelReservationUseCase(movementGateway, projectorGateway, keyGateway);
    }

    @Bean
    public FindMovementsByProfessorInputPort findMovementsByProfessorUseCase(MovementGateway gateway) {
        return new FindMovementsByProfessorUseCase(gateway);
    }

    @Bean
    public RegisterReturnInputPort registerReturnUseCase(
            MovementGateway movementGateway, ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        return new RegisterReturnUseCase(movementGateway, projectorGateway, keyGateway);
    }

    @Bean
    public ListActiveLoansInputPort listActiveLoansUseCase(MovementGateway gateway) {
        return new ListActiveLoansUseCase(gateway);
    }

    @Bean
    public ListOpenReservationsInputPort listOpenReservationsUseCase(MovementGateway gateway) {
        return new ListOpenReservationsUseCase(gateway);
    }

    @Bean
    public ExchangeAssetInputPort exchangeAssetUseCase(
            MovementGateway movementGateway, ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        return new ExchangeAssetUseCase(movementGateway, projectorGateway, keyGateway);
    }

    @Bean
    public FindProfessorPendenciesInputPort findProfessorPendenciesUseCase(MovementGateway gateway) {
        return new FindProfessorPendenciesUseCase(gateway);
    }

    @Bean
    public ReleaseFromMaintenanceInputPort releaseFromMaintenanceUseCase(
            ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        return new ReleaseFromMaintenanceUseCase(projectorGateway, keyGateway);
    }

    @Bean
    public GenerateMovementReportInputPort generateMovementReportUseCase(MovementGateway movementGateway) {
        return new GenerateMovementReportUseCase(movementGateway);
    }

    @Bean
    public ExpireReservationsInputPort expireReservationsUseCase(
            MovementGateway movementGateway, ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        return new ExpireReservationsUseCase(movementGateway, projectorGateway, keyGateway);
    }
}
