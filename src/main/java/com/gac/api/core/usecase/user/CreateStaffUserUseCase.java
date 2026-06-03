package com.gac.api.core.usecase.user;

import com.gac.api.core.domain.Role;
import com.gac.api.core.domain.User;
import com.gac.api.core.exception.BusinessRuleException;

public class CreateStaffUserUseCase {

    private final CreateUserUseCase createUserUseCase;

    public CreateStaffUserUseCase(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    public User execute(User newUser) {
        if (newUser.getRole() == Role.PROFESSOR) {
            throw new BusinessRuleException("Use POST /api/professors to register professors.");
        }
        if (newUser.getRole() != Role.ADMIN && newUser.getRole() != Role.ATTENDANT) {
            throw new BusinessRuleException("Staff users must have role ADMIN or ATTENDANT.");
        }
        return createUserUseCase.execute(newUser);
    }
}
