package com.gac.api.adapter.out.scheduler;

import com.gac.api.application.port.in.movement.ExpireReservationsInputPort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReservationExpirationScheduler {

    private final ExpireReservationsInputPort expireReservationsUseCase;

    public ReservationExpirationScheduler(ExpireReservationsInputPort expireReservationsUseCase) {
        this.expireReservationsUseCase = expireReservationsUseCase;
    }

    @Scheduled(fixedRateString = "${gac.scheduler.expire-reservations-ms:3600000}")
    public void expireReservations() {
        expireReservationsUseCase.execute();
    }
}
