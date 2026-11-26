package com.vitalog.service;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("El correo o la contraseña no son correctos.");
    }
}
