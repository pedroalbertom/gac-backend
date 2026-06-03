package com.gac.api.application.usecase.user;

import com.gac.api.application.port.in.user.CreateStaffUserInputPort;
import com.gac.api.application.port.in.user.CreateUserInputPort;
import com.gac.api.domain.model.Role;
import com.gac.api.domain.model.User;
import com.gac.api.domain.exception.BusinessRuleException;

public class CreateStaffUserUseCase implements CreateStaffUserInputPort {

    private final CreateUserInputPort createUserUseCase;

    public CreateStaffUserUseCase(CreateUserInputPort createUserUseCase) {
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
