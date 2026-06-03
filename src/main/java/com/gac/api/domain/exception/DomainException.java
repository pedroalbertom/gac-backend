package com.gac.api.domain.exception;

public abstract class DomainException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected DomainException(String message) {
        super(message);
    }
}
