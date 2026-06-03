package com.gac.api.application.service.user;

import org.springframework.stereotype.Service;

import com.gac.api.domain.model.Role;
import com.gac.api.domain.model.User;

@Service
public class CreateProfessorService {

    private final CreateUserService createUserService;

    public CreateProfessorService(CreateUserService createUserService) {
        this.createUserService = createUserService;
    }

    public User execute(User professor) {
        professor.setRole(Role.PROFESSOR);
        return createUserService.execute(professor);
    }
}
