package com.gac.api.presentation.controller;

import com.gac.api.core.domain.User;
import com.gac.api.core.usecase.auth.AuthenticateUserUseCase;
import com.gac.api.infrastructure.security.JwtService;
import com.gac.api.presentation.dto.request.LoginRequest;
import com.gac.api.presentation.dto.response.LoginResponse;
import com.gac.api.presentation.mapper.UserMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final JwtService jwtService;

    public AuthController(AuthenticateUserUseCase authenticateUserUseCase, JwtService jwtService) {
        this.authenticateUserUseCase = authenticateUserUseCase;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        User user = authenticateUserUseCase.execute(request.registrationNumber(), request.password());
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new LoginResponse(token, "Bearer", UserMapper.toResponse(user)));
    }
}
