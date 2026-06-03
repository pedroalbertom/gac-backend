package com.gac.api.infrastructure.configuration;

import com.gac.api.core.domain.Role;
import com.gac.api.core.domain.User;
import com.gac.api.core.gateway.PasswordHasher;
import com.gac.api.core.gateway.UserGateway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DevDataInitializer implements CommandLineRunner {

    private final UserGateway userGateway;
    private final PasswordHasher passwordHasher;

    public DevDataInitializer(UserGateway userGateway, PasswordHasher passwordHasher) {
        this.userGateway = userGateway;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public void run(String... args) {
        if (userGateway.findByRegistrationNumber("admin").isPresent()) {
            return;
        }

        User admin = new User();
        admin.setName("Administrator");
        admin.setEmail("admin@gac.local");
        admin.setRegistrationNumber("admin");
        admin.setPassword(passwordHasher.encode("admin123"));
        admin.setRole(Role.ADMIN);
        userGateway.save(admin);
    }
}
