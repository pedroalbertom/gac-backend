package com.gac.api.adapter.out.configuration;

import com.gac.api.application.port.out.KeyGateway;
import com.gac.api.application.port.in.key.CreateKeyInputPort;
import com.gac.api.application.port.in.key.DeleteKeyInputPort;
import com.gac.api.application.port.in.key.GetKeyByIdInputPort;
import com.gac.api.application.port.in.key.ListKeysInputPort;
import com.gac.api.application.port.in.key.UpdateKeyInputPort;
import com.gac.api.application.usecase.key.CreateKeyUseCase;
import com.gac.api.application.usecase.key.DeleteKeyUseCase;
import com.gac.api.application.usecase.key.GetKeyByIdUseCase;
import com.gac.api.application.usecase.key.ListKeysUseCase;
import com.gac.api.application.usecase.key.UpdateKeyUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyConfig {

    @Bean
    public CreateKeyInputPort createKeyUseCase(KeyGateway gateway) {
        return new CreateKeyUseCase(gateway);
    }

    @Bean
    public ListKeysInputPort listKeysUseCase(KeyGateway gateway) {
        return new ListKeysUseCase(gateway);
    }

    @Bean
    public GetKeyByIdInputPort getKeyByIdUseCase(KeyGateway gateway) {
        return new GetKeyByIdUseCase(gateway);
    }

    @Bean
    public UpdateKeyInputPort updateKeyUseCase(KeyGateway gateway) {
        return new UpdateKeyUseCase(gateway);
    }

    @Bean
    public DeleteKeyInputPort deleteKeyUseCase(KeyGateway gateway) {
        return new DeleteKeyUseCase(gateway);
    }
}
