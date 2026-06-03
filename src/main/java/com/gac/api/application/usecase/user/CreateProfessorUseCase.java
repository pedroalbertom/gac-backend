package com.gac.api.application.usecase.user;

import com.gac.api.application.port.in.user.CreateProfessorInputPort;
import com.gac.api.application.port.in.user.CreateUserInputPort;
import com.gac.api.domain.model.Role;
import com.gac.api.domain.model.User;

public class CreateProfessorUseCase implements CreateProfessorInputPort {

    private final CreateUserInputPort createUserUseCase;

    public CreateProfessorUseCase(CreateUserInputPort createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    public User execute(User professor) {
        professor.setRole(Role.PROFESSOR);
        return createUserUseCase.execute(professor);
    }
}
