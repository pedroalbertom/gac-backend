package com.gac.api.application.service.user;

import org.springframework.stereotype.Service;

import com.gac.api.domain.model.Role;
import com.gac.api.domain.model.User;
import com.gac.api.application.repository.UserRepository;
import java.util.List;

@Service
public class ListProfessorsService {

    private final UserRepository userRepository;

    public ListProfessorsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> execute() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole() == Role.PROFESSOR)
                .toList();
    }
}
