package com.gac.api.infrastructure.configuration;

import com.gac.api.core.domain.ItemStatus;
import com.gac.api.core.domain.Key;
import com.gac.api.core.domain.Projector;
import com.gac.api.core.domain.Role;
import com.gac.api.core.domain.User;
import com.gac.api.core.gateway.KeyGateway;
import com.gac.api.core.gateway.PasswordHasher;
import com.gac.api.core.gateway.ProjectorGateway;
import com.gac.api.core.gateway.UserGateway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DevDataInitializer implements CommandLineRunner {

    private final UserGateway userGateway;
    private final PasswordHasher passwordHasher;
    private final ProjectorGateway projectorGateway;
    private final KeyGateway keyGateway;

    public DevDataInitializer(
            UserGateway userGateway,
            PasswordHasher passwordHasher,
            ProjectorGateway projectorGateway,
            KeyGateway keyGateway) {
        this.userGateway = userGateway;
        this.passwordHasher = passwordHasher;
        this.projectorGateway = projectorGateway;
        this.keyGateway = keyGateway;
    }

    @Override
    public void run(String... args) {
        seedUser("admin", "Administrator", "admin@gac.local", Role.ADMIN, "admin123");
        seedUser("atendente", "Attendant", "atendente@gac.local", Role.ATTENDANT, "atendente123");
        seedUser("professor", "Professor Demo", "professor@gac.local", Role.PROFESSOR, "prof123");
        seedAssets();
    }

    private void seedUser(
            String registrationNumber, String name, String email, Role role, String plainPassword) {
        if (userGateway.findByRegistrationNumber(registrationNumber).isPresent()) {
            return;
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRegistrationNumber(registrationNumber);
        user.setPassword(passwordHasher.encode(plainPassword));
        user.setRole(role);
        userGateway.save(user);
    }

    private void seedAssets() {
        if (projectorGateway.findAll().isEmpty()) {
            Projector projector = new Projector();
            projector.setBrand("Epson");
            projector.setModel("PowerLite");
            projector.setSerialNumber("SN-DEMO-001");
            projector.setAssetTag("PAT-001");
            projector.setStatus(ItemStatus.AVAILABLE);
            projectorGateway.save(projector);
        }

        if (keyGateway.findAll().isEmpty()) {
            Key key = new Key();
            key.setRoom("101");
            key.setBlock("A");
            key.setAssetTag("KEY-001");
            key.setSpareKey(false);
            key.setStatus(ItemStatus.AVAILABLE);
            keyGateway.save(key);
        }
    }
}
