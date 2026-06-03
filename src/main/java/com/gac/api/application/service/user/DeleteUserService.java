package com.gac.api.application.service.user;

import org.springframework.stereotype.Service;

import com.gac.api.domain.exception.NotFoundException;
import com.gac.api.application.repository.UserRepository;

@Service
public class DeleteUserService {

    private final UserRepository userRepository;

    public DeleteUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new NotFoundException("User not found.");
        }
        userRepository.deleteById(id);
    }
}
