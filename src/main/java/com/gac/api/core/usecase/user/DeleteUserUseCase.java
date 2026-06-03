package com.gac.api.core.usecase.user;

import com.gac.api.core.exception.NotFoundException;
import com.gac.api.core.gateway.UserGateway;

public class DeleteUserUseCase {

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
