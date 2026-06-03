package com.gac.api.application.port.in.user;

import com.gac.api.domain.model.User;

public interface GetUserByIdInputPort {
    User execute(Long id);
}
