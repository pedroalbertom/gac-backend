package com.gac.api.application.port.in.user;

public interface ChangePasswordInputPort {
    void execute(Long userId, String currentPassword, String newPassword);
}
