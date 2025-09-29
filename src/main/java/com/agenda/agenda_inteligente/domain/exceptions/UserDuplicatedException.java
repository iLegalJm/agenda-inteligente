package com.agenda.agenda_inteligente.domain.exceptions;

public class UserDuplicatedException extends RuntimeException {
    public UserDuplicatedException(String message) {
        super(message);
    }
}
