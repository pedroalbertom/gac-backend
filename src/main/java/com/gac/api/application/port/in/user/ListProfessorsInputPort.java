package com.gac.api.application.port.in.user;

import com.gac.api.domain.model.User;
import java.util.List;

public interface ListProfessorsInputPort {
    List<User> execute();
}
