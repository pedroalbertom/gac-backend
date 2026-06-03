package com.gac.api.application.port.in.user;

import com.gac.api.domain.model.User;

public interface UpdateUserInputPort {
    User execute(Long id, User updatedData);
}
