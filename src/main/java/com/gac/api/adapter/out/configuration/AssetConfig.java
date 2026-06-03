package com.gac.api.adapter.out.configuration;

import com.gac.api.application.port.out.KeyGateway;
import com.gac.api.application.port.out.ProjectorGateway;
import com.gac.api.application.port.in.asset.SearchAssetsInputPort;
import com.gac.api.application.usecase.asset.SearchAssetsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AssetConfig {

    @Bean
    public SearchAssetsInputPort searchAssetsUseCase(ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        return new SearchAssetsUseCase(projectorGateway, keyGateway);
    }
}
