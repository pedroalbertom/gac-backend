package com.gac.api.infrastructure.configuration;

import com.gac.api.core.gateway.KeyGateway;
import com.gac.api.core.usecase.key.CreateKeyUseCase;
import com.gac.api.core.usecase.key.DeleteKeyUseCase;
import com.gac.api.core.usecase.key.ListKeysUseCase;
import com.gac.api.core.usecase.key.UpdateKeyUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyConfig {

    @Bean
    public CreateKeyUseCase createKeyUseCase(KeyGateway gateway) {
        return new CreateKeyUseCase(gateway);
    }

    @Bean
    public ListKeysUseCase listKeysUseCase(KeyGateway gateway) {
        return new ListKeysUseCase(gateway);
    }

    @Bean
    public UpdateKeyUseCase updateKeyUseCase(KeyGateway gateway) {
        return new UpdateKeyUseCase(gateway);
    }

    @Bean
    public DeleteKeyUseCase deleteKeyUseCase(KeyGateway gateway) {
        return new DeleteKeyUseCase(gateway);
    }
}
