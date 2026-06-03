package com.gac.api.adapter.out.configuration;

import com.gac.api.domain.port.ProjectorGateway;
import com.gac.api.application.port.in.projector.CreateProjectorInputPort;
import com.gac.api.application.port.in.projector.DeleteProjectorInputPort;
import com.gac.api.application.port.in.projector.GetProjectorByIdInputPort;
import com.gac.api.application.port.in.projector.ListProjectorsInputPort;
import com.gac.api.application.port.in.projector.UpdateProjectorInputPort;
import com.gac.api.application.usecase.projector.CreateProjectorUseCase;
import com.gac.api.application.usecase.projector.DeleteProjectorUseCase;
import com.gac.api.application.usecase.projector.GetProjectorByIdUseCase;
import com.gac.api.application.usecase.projector.ListProjectorsUseCase;
import com.gac.api.application.usecase.projector.UpdateProjectorUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectorConfig {

    @Bean
    public CreateProjectorInputPort createProjectorUseCase(ProjectorGateway gateway) {
        return new CreateProjectorUseCase(gateway);
    }

    @Bean
    public ListProjectorsInputPort listProjectorsUseCase(ProjectorGateway gateway) {
        return new ListProjectorsUseCase(gateway);
    }

    @Bean
    public GetProjectorByIdInputPort getProjectorByIdUseCase(ProjectorGateway gateway) {
        return new GetProjectorByIdUseCase(gateway);
    }

    @Bean
    public UpdateProjectorInputPort updateProjectorUseCase(ProjectorGateway gateway) {
        return new UpdateProjectorUseCase(gateway);
    }

    @Bean
    public DeleteProjectorInputPort deleteProjectorUseCase(ProjectorGateway gateway) {
        return new DeleteProjectorUseCase(gateway);
    }
}
