package com.gac.api.application.port.in.auth;

import com.gac.api.domain.model.User;

public interface AuthenticateUserInputPort {
    User execute(String registrationNumber, String rawPassword);
}
