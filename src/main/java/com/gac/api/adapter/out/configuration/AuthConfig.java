package com.gac.api.adapter.out.configuration;

import com.gac.api.application.port.out.PasswordHasher;
import com.gac.api.domain.port.UserGateway;
import com.gac.api.application.port.in.auth.AuthenticateUserInputPort;
import com.gac.api.application.port.in.user.ChangePasswordInputPort;
import com.gac.api.application.port.in.user.GetUserByIdInputPort;
import com.gac.api.application.usecase.auth.AuthenticateUserUseCase;
import com.gac.api.application.usecase.user.ChangePasswordUseCase;
import com.gac.api.application.usecase.user.GetUserByIdUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {

    @Bean
    public AuthenticateUserInputPort authenticateUserUseCase(UserGateway userGateway, PasswordHasher passwordHasher) {
        return new AuthenticateUserUseCase(userGateway, passwordHasher);
    }

    @Bean
    public ChangePasswordInputPort changePasswordUseCase(UserGateway userGateway, PasswordHasher passwordHasher) {
        return new ChangePasswordUseCase(userGateway, passwordHasher);
    }

    @Bean
    public GetUserByIdInputPort getUserByIdUseCase(UserGateway userGateway) {
        return new GetUserByIdUseCase(userGateway);
    }
}
