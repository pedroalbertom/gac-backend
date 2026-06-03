package com.gac.api.domain.exception;

public class UnauthorizedException extends DomainException {

    private static final long serialVersionUID = 1L;

    public UnauthorizedException(String message) {
        super(message);
    }
}
