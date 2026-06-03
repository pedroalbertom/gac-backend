package com.gac.api.application.usecase.user;

import com.gac.api.application.port.in.user.ListProfessorsInputPort;

import com.gac.api.domain.model.Role;
import com.gac.api.domain.model.User;
import com.gac.api.application.port.out.UserGateway;
import java.util.List;

public class ListProfessorsUseCase implements ListProfessorsInputPort {

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
