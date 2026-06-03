package com.gac.api.core.usecase.user;

import com.gac.api.core.domain.Role;
import com.gac.api.core.domain.User;

public class CreateProfessorUseCase {

    private final CreateUserUseCase createUserUseCase;

    public CreateProfessorUseCase(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    public User execute(User professor) {
        professor.setRole(Role.PROFESSOR);
        return createUserUseCase.execute(professor);
    }
}
