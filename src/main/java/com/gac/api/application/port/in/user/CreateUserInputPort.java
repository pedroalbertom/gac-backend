package com.gac.api.application.port.in.user;

import com.gac.api.domain.model.User;

public interface CreateUserInputPort {
    User execute(User newUser);
}
