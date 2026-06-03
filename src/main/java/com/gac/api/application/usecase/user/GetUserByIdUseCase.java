package com.gac.api.application.usecase.user;

import com.gac.api.application.port.in.user.GetUserByIdInputPort;

import com.gac.api.domain.model.User;
import com.gac.api.domain.exception.NotFoundException;
import com.gac.api.application.port.out.UserGateway;

public class GetUserByIdUseCase implements GetUserByIdInputPort {

    private final UserGateway userGateway;

    public GetUserByIdUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User execute(Long id) {
        return userGateway.findById(id).orElseThrow(() -> new NotFoundException("User not found."));
    }
}
