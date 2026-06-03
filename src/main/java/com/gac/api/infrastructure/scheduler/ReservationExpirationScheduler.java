package com.gac.api.infrastructure.scheduler;

import com.gac.api.application.service.movement.ExpireReservationsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReservationExpirationScheduler {

    private final ExpireReservationsService expireReservationsUseCase;

    public ReservationExpirationScheduler(ExpireReservationsService expireReservationsUseCase) {
        this.expireReservationsUseCase = expireReservationsUseCase;
    }

    @Scheduled(fixedRateString = "${gac.scheduler.expire-reservations-ms:3600000}")
    public void expireReservations() {
        expireReservationsUseCase.execute();
    }
}
