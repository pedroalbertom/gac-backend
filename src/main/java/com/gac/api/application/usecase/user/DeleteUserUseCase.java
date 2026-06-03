package com.gac.api.application.usecase.user;

import com.gac.api.application.port.in.user.DeleteUserInputPort;

import com.gac.api.domain.exception.NotFoundException;
import com.gac.api.application.port.out.UserGateway;

public class DeleteUserUseCase implements DeleteUserInputPort {

    private final UserGateway userGateway;

    public DeleteUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public void execute(Long id) {
        if (userGateway.findById(id).isEmpty()) {
            throw new NotFoundException("User not found.");
        }
        userGateway.deleteById(id);
    }
}
