package com.gac.api.core.usecase.user;

import com.gac.api.core.domain.User;
import com.gac.api.core.gateway.UserGateway;
import java.util.List;

public class ListUsersUseCase {

    private final UserGateway userGateway;

    public ListUsersUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public List<User> execute() {
        return userGateway.findAll();
    }
}
