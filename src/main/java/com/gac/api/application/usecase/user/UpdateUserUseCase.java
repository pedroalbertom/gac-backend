package com.gac.api.application.usecase.user;

import com.gac.api.application.port.in.user.UpdateUserInputPort;

import com.gac.api.domain.model.User;
import com.gac.api.domain.exception.ConflictException;
import com.gac.api.domain.exception.NotFoundException;
import com.gac.api.domain.port.UserGateway;

public class UpdateUserUseCase implements UpdateUserInputPort {

    private final UserGateway userGateway;

    public UpdateUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User execute(Long id, User updatedData) {
        User existing = userGateway.findById(id).orElseThrow(() -> new NotFoundException("User not found."));

        if (!existing.getEmail().equalsIgnoreCase(updatedData.getEmail())) {
            userGateway.findByEmail(updatedData.getEmail()).ifPresent(user -> {
                if (!user.getId().equals(id)) {
                    throw new ConflictException("Email is already in use.");
                }
            });
        }

        existing.setName(updatedData.getName());
        existing.setEmail(updatedData.getEmail());
        existing.setRole(updatedData.getRole());

        return userGateway.save(existing);
    }
}
