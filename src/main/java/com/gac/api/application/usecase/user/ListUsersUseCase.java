package com.gac.api.application.usecase.user;

import com.gac.api.application.port.in.user.ListUsersInputPort;

import com.gac.api.domain.model.User;
import com.gac.api.domain.port.UserGateway;
import java.util.List;

public class ListUsersUseCase implements ListUsersInputPort {

    private final UserGateway userGateway;

    public ListUsersUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public List<User> execute() {
        return userGateway.findAll();
    }
}
