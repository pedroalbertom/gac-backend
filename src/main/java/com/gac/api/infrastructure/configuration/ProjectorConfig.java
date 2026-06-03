package com.gac.api.infrastructure.configuration;

import com.gac.api.core.gateway.ProjectorGateway;
import com.gac.api.core.usecase.projector.CreateProjectorUseCase;
import com.gac.api.core.usecase.projector.DeleteProjectorUseCase;
import com.gac.api.core.usecase.projector.GetProjectorByIdUseCase;
import com.gac.api.core.usecase.projector.ListProjectorsUseCase;
import com.gac.api.core.usecase.projector.UpdateProjectorUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectorConfig {

    @Bean
    public CreateProjectorUseCase createProjectorUseCase(ProjectorGateway gateway) {
        return new CreateProjectorUseCase(gateway);
    }

    @Bean
    public ListProjectorsUseCase listProjectorsUseCase(ProjectorGateway gateway) {
        return new ListProjectorsUseCase(gateway);
    }

    @Bean
    public GetProjectorByIdUseCase getProjectorByIdUseCase(ProjectorGateway gateway) {
        return new GetProjectorByIdUseCase(gateway);
    }

    @Bean
    public UpdateProjectorUseCase updateProjectorUseCase(ProjectorGateway gateway) {
        return new UpdateProjectorUseCase(gateway);
    }

    @Bean
    public DeleteProjectorUseCase deleteProjectorUseCase(ProjectorGateway gateway) {
        return new DeleteProjectorUseCase(gateway);
    }
}
