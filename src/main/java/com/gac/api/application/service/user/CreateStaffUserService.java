package com.gac.api.application.service.user;

import org.springframework.stereotype.Service;

import com.gac.api.domain.model.Role;
import com.gac.api.domain.model.User;
import com.gac.api.domain.exception.BusinessRuleException;

@Service
public class CreateStaffUserService {

    private final CreateUserService createUserService;

    public CreateStaffUserService(CreateUserService createUserService) {
        this.createUserService = createUserService;
    }

    public User execute(User newUser) {
        if (newUser.getRole() == Role.PROFESSOR) {
            throw new BusinessRuleException("Use POST /api/professors to register professors.");
        }
        if (newUser.getRole() != Role.ADMIN && newUser.getRole() != Role.ATTENDANT) {
            throw new BusinessRuleException("Staff users must have role ADMIN or ATTENDANT.");
        }
        return createUserService.execute(newUser);
    }
}
