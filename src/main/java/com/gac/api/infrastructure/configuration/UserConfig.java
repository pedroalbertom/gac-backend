package com.gac.api.infrastructure.configuration;

import com.gac.api.core.gateway.PasswordHasher;
import com.gac.api.core.gateway.UserGateway;
import com.gac.api.core.usecase.user.CreateProfessorUseCase;
import com.gac.api.core.usecase.user.CreateStaffUserUseCase;
import com.gac.api.core.usecase.user.CreateUserUseCase;
import com.gac.api.core.usecase.user.DeleteUserUseCase;
import com.gac.api.core.usecase.user.ListProfessorsUseCase;
import com.gac.api.core.usecase.user.ListUsersUseCase;
import com.gac.api.core.usecase.user.UpdateUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public CreateUserUseCase createUserUseCase(UserGateway gateway, PasswordHasher passwordHasher) {
        return new CreateUserUseCase(gateway, passwordHasher);
    }

    @Bean
    public CreateStaffUserUseCase createStaffUserUseCase(CreateUserUseCase createUserUseCase) {
        return new CreateStaffUserUseCase(createUserUseCase);
    }

    @Bean
    public CreateProfessorUseCase createProfessorUseCase(CreateUserUseCase createUserUseCase) {
        return new CreateProfessorUseCase(createUserUseCase);
    }

    @Bean
    public ListUsersUseCase listUsersUseCase(UserGateway gateway) {
        return new ListUsersUseCase(gateway);
    }

    @Bean
    public ListProfessorsUseCase listProfessorsUseCase(UserGateway gateway) {
        return new ListProfessorsUseCase(gateway);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(UserGateway gateway) {
        return new UpdateUserUseCase(gateway);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(UserGateway gateway) {
        return new DeleteUserUseCase(gateway);
    }
}
