package com.gac.api.infrastructure.configuration;

import com.gac.api.core.gateway.UserGateway;
import com.gac.api.core.usecase.user.CreateUserUseCase;
import com.gac.api.core.usecase.user.DeleteUserUseCase;
import com.gac.api.core.usecase.user.ListUsersUseCase;
import com.gac.api.core.usecase.user.UpdateUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public CreateUserUseCase createUserUseCase(UserGateway gateway) {
        return new CreateUserUseCase(gateway);
    }

    @Bean
    public ListUsersUseCase listUsersUseCase(UserGateway gateway) {
        return new ListUsersUseCase(gateway);
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
