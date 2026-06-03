package com.gac.api.core.usecase.user;

import com.gac.api.core.domain.Role;
import com.gac.api.core.domain.User;
import com.gac.api.core.gateway.UserGateway;
import java.util.List;

public class ListProfessorsUseCase {

    private final UserGateway userGateway;

    public ListProfessorsUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public List<User> execute() {
        return userGateway.findAll().stream()
                .filter(user -> user.getRole() == Role.PROFESSOR)
                .toList();
    }
}
