package com.gac.api.application.port.in.movement;

import com.gac.api.domain.model.Pendency;
import java.util.List;

public interface FindProfessorPendenciesInputPort {
    List<Pendency> execute(String professorRegistrationNumber);
}
