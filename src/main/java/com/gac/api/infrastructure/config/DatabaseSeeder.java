package com.gac.api.infrastructure.config;

import com.gac.api.domain.model.Role;
import com.gac.api.domain.model.ItemStatus;
import com.gac.api.domain.model.MovementType;
import com.gac.api.domain.model.MovementStatus;
import com.gac.api.domain.model.AssetType;
import com.gac.api.infrastructure.persistence.user.UserEntity;
import com.gac.api.infrastructure.persistence.user.UserJpaRepository;
import com.gac.api.infrastructure.persistence.key.KeyEntity;
import com.gac.api.infrastructure.persistence.key.KeyJpaRepository;
import com.gac.api.infrastructure.persistence.projector.ProjectorEntity;
import com.gac.api.infrastructure.persistence.projector.ProjectorJpaRepository;
import com.gac.api.infrastructure.persistence.movement.MovementEntity;
import com.gac.api.infrastructure.persistence.movement.MovementJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final UserJpaRepository userRepository;
    private final KeyJpaRepository keyRepository;
    private final ProjectorJpaRepository projectorRepository;
    private final MovementJpaRepository movementRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(UserJpaRepository userRepository,
                          KeyJpaRepository keyRepository,
                          ProjectorJpaRepository projectorRepository,
                          MovementJpaRepository movementRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.keyRepository = keyRepository;
        this.projectorRepository = projectorRepository;
        this.movementRepository = movementRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() > 0) {
            return; // Already seeded
        }

        String defaultPassword = passwordEncoder.encode("password123");

        // Seed Users
        UserEntity admin = new UserEntity(null, "Carlos Silva", "carlos.admin@gac.local", "10001", defaultPassword, Role.ADMIN);
        UserEntity attendant1 = new UserEntity(null, "Mariana Costa", "mariana.atendente@gac.local", "20001", defaultPassword, Role.ATTENDANT);
        UserEntity attendant2 = new UserEntity(null, "Bruno Santos", "bruno.atendente@gac.local", "20002", defaultPassword, Role.ATTENDANT);
        UserEntity professor1 = new UserEntity(null, "Dr. André Oliveira", "andre.professor@gac.local", "30001", defaultPassword, Role.PROFESSOR);
        UserEntity professor2 = new UserEntity(null, "Dra. Patricia Lima", "patricia.professor@gac.local", "30002", defaultPassword, Role.PROFESSOR);
        UserEntity professor3 = new UserEntity(null, "Dr. Ricardo Souza", "ricardo.professor@gac.local", "30003", defaultPassword, Role.PROFESSOR);

        userRepository.saveAll(Arrays.asList(admin, attendant1, attendant2, professor1, professor2, professor3));

        // Retrieve saved entities to make sure we have IDs
        List<UserEntity> savedUsers = userRepository.findAll();
        UserEntity dbAttendant1 = savedUsers.stream().filter(u -> u.getRegistrationNumber().equals("20001")).findFirst().orElse(attendant1);
        UserEntity dbAttendant2 = savedUsers.stream().filter(u -> u.getRegistrationNumber().equals("20002")).findFirst().orElse(attendant2);

        // Seed Projectors
        ProjectorEntity proj1 = new ProjectorEntity(null, "Epson", "PowerLite X41+", "EPS-987654321", "PROJ-001", ItemStatus.AVAILABLE, null, null);
        ProjectorEntity proj2 = new ProjectorEntity(null, "BenQ", "MX535", "BENQ-123456789", "PROJ-002", ItemStatus.ON_LOAN, null, null);
        ProjectorEntity proj3 = new ProjectorEntity(null, "Sony", "VPL-DX221", "SONY-555666777", "PROJ-003", ItemStatus.RESERVED, "30001", null);
        ProjectorEntity proj4 = new ProjectorEntity(null, "Epson", "PowerLite W49", "EPS-111222333", "PROJ-004", ItemStatus.MAINTENANCE, null, "Lâmpada queimada");
        ProjectorEntity proj5 = new ProjectorEntity(null, "LG", "CineBeam", "LG-444555666", "PROJ-005", ItemStatus.AVAILABLE, null, null);

        projectorRepository.saveAll(Arrays.asList(proj1, proj2, proj3, proj4, proj5));

        List<ProjectorEntity> savedProjectors = projectorRepository.findAll();
        ProjectorEntity dbProj1 = savedProjectors.stream().filter(p -> p.getAssetTag().equals("PROJ-001")).findFirst().orElse(proj1);
        ProjectorEntity dbProj2 = savedProjectors.stream().filter(p -> p.getAssetTag().equals("PROJ-002")).findFirst().orElse(proj2);
        ProjectorEntity dbProj3 = savedProjectors.stream().filter(p -> p.getAssetTag().equals("PROJ-003")).findFirst().orElse(proj3);

        // Seed Keys
        KeyEntity key1 = new KeyEntity(null, "101", "A", "KEY-A101", false, ItemStatus.AVAILABLE, null, null);
        KeyEntity key2 = new KeyEntity(null, "101", "A", "KEY-A101-R", true, ItemStatus.AVAILABLE, null, null);
        KeyEntity key3 = new KeyEntity(null, "203", "B", "KEY-B203", false, ItemStatus.ON_LOAN, null, null);
        KeyEntity key4 = new KeyEntity(null, "305", "C", "KEY-C305", false, ItemStatus.RESERVED, "30002", null);
        KeyEntity key5 = new KeyEntity(null, "402", "D", "KEY-D402", false, ItemStatus.MAINTENANCE, null, "Fechadura emperrada");
        KeyEntity key6 = new KeyEntity(null, "402", "D", "KEY-D402-R", true, ItemStatus.AVAILABLE, null, null);

        keyRepository.saveAll(Arrays.asList(key1, key2, key3, key4, key5, key6));

        List<KeyEntity> savedKeys = keyRepository.findAll();
        KeyEntity dbKey3 = savedKeys.stream().filter(k -> k.getAssetTag().equals("KEY-B203")).findFirst().orElse(key3);
        KeyEntity dbKey4 = savedKeys.stream().filter(k -> k.getAssetTag().equals("KEY-C305")).findFirst().orElse(key4);

        // Seed Movements
        MovementEntity reservation1 = new MovementEntity(
                null,
                MovementType.RESERVATION,
                MovementStatus.OPEN,
                "30001",
                null,
                AssetType.PROJECTOR,
                dbProj3.getId(),
                "1234",
                "Aula de Programação Orientada a Objetos",
                "Sala A-12",
                null,
                null,
                null,
                LocalDateTime.now().minusMinutes(30),
                List.of(),
                List.of()
        );

        MovementEntity reservation2 = new MovementEntity(
                null,
                MovementType.RESERVATION,
                MovementStatus.OPEN,
                "30002",
                null,
                AssetType.KEY,
                dbKey4.getId(),
                "5678",
                "Reunião de Colegiado",
                "Sala C-305",
                null,
                null,
                null,
                LocalDateTime.now().minusMinutes(15),
                List.of(),
                List.of()
        );

        MovementEntity loan1 = new MovementEntity(
                null,
                MovementType.LOAN,
                MovementStatus.OPEN,
                "30003",
                dbAttendant1,
                AssetType.PROJECTOR,
                dbProj2.getId(),
                null,
                "Apresentação de TCC",
                "Auditório Bloco B",
                null,
                LocalDateTime.now().minusHours(2),
                null,
                LocalDateTime.now().minusHours(2),
                List.of("Controle Remoto", "Cabo HDMI"),
                List.of()
        );

        MovementEntity loan2 = new MovementEntity(
                null,
                MovementType.LOAN,
                MovementStatus.OPEN,
                "30001",
                dbAttendant2,
                AssetType.KEY,
                dbKey3.getId(),
                null,
                "Aula Prática Laboratório",
                "Sala B-203",
                null,
                LocalDateTime.now().minusHours(1),
                null,
                LocalDateTime.now().minusHours(1),
                List.of(),
                List.of()
        );

        MovementEntity completedLoan = new MovementEntity(
                null,
                MovementType.RETURN,
                MovementStatus.COMPLETED,
                "30002",
                dbAttendant1,
                AssetType.PROJECTOR,
                dbProj1.getId(),
                null,
                "Minicurso IA",
                "Teatro Celina Queiroz",
                null,
                LocalDateTime.now().minusDays(1).minusHours(4),
                LocalDateTime.now().minusDays(1).minusHours(2),
                LocalDateTime.now().minusDays(1).minusHours(4),
                List.of("Cabo HDMI", "Adaptador VGA"),
                List.of("Cabo HDMI", "Adaptador VGA")
        );

        movementRepository.saveAll(Arrays.asList(reservation1, reservation2, loan1, loan2, completedLoan));
    }
}
