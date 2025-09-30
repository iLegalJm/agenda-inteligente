package com.agenda.agenda_inteligente.application.ports.input;

import java.util.Map;

import com.agenda.agenda_inteligente.domain.model.User;

public interface AuthServicePort {
    Map<String, String> login(String email, String password);

    User register(User user);

    Map<String, String> refreshToken(String refreshToken);

    void logout(String refreshToken);

    // User getAuthenticaUser();
}
