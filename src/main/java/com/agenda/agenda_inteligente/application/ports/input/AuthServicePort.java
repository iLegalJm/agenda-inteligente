package com.agenda.agenda_inteligente.application.ports.input;

import java.util.Map;

import com.agenda.agenda_inteligente.domain.model.User;

public interface AuthServicePort {
    public Map<String, String> login(String email, String password);

    public User register(User user);

    public Map<String, String> refreshToken(String refreshToken);

    public void logout(String refreshToken);
}
