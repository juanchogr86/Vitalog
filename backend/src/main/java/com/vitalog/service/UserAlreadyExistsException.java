package com.vitalog.service;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super("Ya existe una cuenta con ese correo electrónico.");
    }
}
