package com.gac.api.core.usecase.user;

import com.gac.api.core.domain.User;
import com.gac.api.core.gateway.UserGateway;

public class UpdateUserUseCase {

    private final UserGateway userGateway;

    public UpdateUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User execute(Long id, User updatedData) {
        User existing = userGateway.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found."));

        existing.setName(updatedData.getName());
        existing.setEmail(updatedData.getEmail());
        existing.setRole(updatedData.getRole());

        return userGateway.save(existing);
    }
}
