package com.gac.api.adapter.out.configuration;

import com.gac.api.application.port.out.PasswordHasher;
import com.gac.api.domain.port.UserGateway;
import com.gac.api.application.port.in.user.CreateProfessorInputPort;
import com.gac.api.application.port.in.user.CreateStaffUserInputPort;
import com.gac.api.application.port.in.user.CreateUserInputPort;
import com.gac.api.application.port.in.user.DeleteUserInputPort;
import com.gac.api.application.port.in.user.ListProfessorsInputPort;
import com.gac.api.application.port.in.user.ListUsersInputPort;
import com.gac.api.application.port.in.user.UpdateUserInputPort;
import com.gac.api.application.usecase.user.CreateProfessorUseCase;
import com.gac.api.application.usecase.user.CreateStaffUserUseCase;
import com.gac.api.application.usecase.user.CreateUserUseCase;
import com.gac.api.application.usecase.user.DeleteUserUseCase;
import com.gac.api.application.usecase.user.ListProfessorsUseCase;
import com.gac.api.application.usecase.user.ListUsersUseCase;
import com.gac.api.application.usecase.user.UpdateUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public CreateUserInputPort createUserUseCase(UserGateway gateway, PasswordHasher passwordHasher) {
        return new CreateUserUseCase(gateway, passwordHasher);
    }

    @Bean
    public CreateStaffUserInputPort createStaffUserUseCase(CreateUserInputPort createUserUseCase) {
        return new CreateStaffUserUseCase(createUserUseCase);
    }

    @Bean
    public CreateProfessorInputPort createProfessorUseCase(CreateUserInputPort createUserUseCase) {
        return new CreateProfessorUseCase(createUserUseCase);
    }

    @Bean
    public ListUsersInputPort listUsersUseCase(UserGateway gateway) {
        return new ListUsersUseCase(gateway);
    }

    @Bean
    public ListProfessorsInputPort listProfessorsUseCase(UserGateway gateway) {
        return new ListProfessorsUseCase(gateway);
    }

    @Bean
    public UpdateUserInputPort updateUserUseCase(UserGateway gateway) {
        return new UpdateUserUseCase(gateway);
    }

    @Bean
    public DeleteUserInputPort deleteUserUseCase(UserGateway gateway) {
        return new DeleteUserUseCase(gateway);
    }
}
