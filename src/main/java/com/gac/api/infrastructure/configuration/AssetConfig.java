package com.gac.api.infrastructure.configuration;

import com.gac.api.core.gateway.KeyGateway;
import com.gac.api.core.gateway.ProjectorGateway;
import com.gac.api.core.usecase.asset.SearchAssetsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AssetConfig {

    @Bean
    public SearchAssetsUseCase searchAssetsUseCase(ProjectorGateway projectorGateway, KeyGateway keyGateway) {
        return new SearchAssetsUseCase(projectorGateway, keyGateway);
    }
}
