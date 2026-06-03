package com.gac.api.application.service.user;

import org.springframework.stereotype.Service;

import com.gac.api.domain.model.User;
import com.gac.api.domain.exception.ConflictException;
import com.gac.api.domain.exception.NotFoundException;
import com.gac.api.application.repository.UserRepository;

@Service
public class UpdateUserService {

    private final UserRepository userRepository;

    public UpdateUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(Long id, User updatedData) {
        User existing = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found."));

        if (!existing.getEmail().equalsIgnoreCase(updatedData.getEmail())) {
            userRepository.findByEmail(updatedData.getEmail()).ifPresent(user -> {
                if (!user.getId().equals(id)) {
                    throw new ConflictException("Email is already in use.");
                }
            });
        }

        existing.setName(updatedData.getName());
        existing.setEmail(updatedData.getEmail());
        existing.setRole(updatedData.getRole());

        return userRepository.save(existing);
    }
}
