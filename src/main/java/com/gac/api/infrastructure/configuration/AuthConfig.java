package com.gac.api.infrastructure.configuration;

import com.gac.api.core.gateway.PasswordHasher;
import com.gac.api.core.gateway.UserGateway;
import com.gac.api.core.usecase.auth.AuthenticateUserUseCase;
import com.gac.api.core.usecase.user.ChangePasswordUseCase;
import com.gac.api.core.usecase.user.GetUserByIdUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {

    @Bean
    public AuthenticateUserUseCase authenticateUserUseCase(UserGateway userGateway, PasswordHasher passwordHasher) {
        return new AuthenticateUserUseCase(userGateway, passwordHasher);
    }

    @Bean
    public ChangePasswordUseCase changePasswordUseCase(UserGateway userGateway, PasswordHasher passwordHasher) {
        return new ChangePasswordUseCase(userGateway, passwordHasher);
    }

    @Bean
    public GetUserByIdUseCase getUserByIdUseCase(UserGateway userGateway) {
        return new GetUserByIdUseCase(userGateway);
    }
}
