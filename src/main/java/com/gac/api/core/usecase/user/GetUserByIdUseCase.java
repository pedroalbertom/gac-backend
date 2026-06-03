package com.gac.api.core.usecase.user;

import com.gac.api.core.domain.User;
import com.gac.api.core.exception.NotFoundException;
import com.gac.api.core.gateway.UserGateway;

public class GetUserByIdUseCase {

    private final UserGateway userGateway;

    public GetUserByIdUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User execute(Long id) {
        return userGateway.findById(id).orElseThrow(() -> new NotFoundException("User not found."));
    }
}
