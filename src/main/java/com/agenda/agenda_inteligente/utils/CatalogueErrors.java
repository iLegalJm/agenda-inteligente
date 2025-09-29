package com.agenda.agenda_inteligente.utils;

import lombok.Getter;

@Getter
public enum CatalogueErrors {
    // MENSAJES DE ERROR PARA USER
    USER_NOT_FOUND("001", "Usuario no encontrado", "El usuario con las credenciales proporcionadas no existe."),
    USER_EMAIL_ALREADY_EXISTS("002", "El email ya está en uso", "El email proporcionado ya está registrado."),

    // MENSAJES DE ERROR PARA REFRESH TOKEN
    REFRESH_TOKEN_NOT_FOUND("101", "Refresh token no encontrado",
            "El refresh token proporcionado no existe o es inválido."),
    REFRESH_TOKEN_EXPIRED("102", "Refresh token expirado", "El refresh token ha expirado."),
    REFRESH_TOKEN_REVOKED("103", "Refresh token revocado", "El refresh token ha sido revocado."),

    // MENSAJES DE ERROR PARA VALIDACIONES
    REQUEST_INVALID("201", "Solicitud inválida", "Los datos proporcionados no son válidos."),

    // MENSAJES DE ERROR GENERICOS
    ERROR_GENERICO("999", "Error genérico", "Ha ocurrido un error inesperado"),

    // ACCESO DENEGADO("403", "Acceso denegado", "No tienes permisos para acceder a este recurso.");
    ACCESS_DENIED("403", "Acceso denegado", "No tienes permisos para acceder a este recurso.");

    private final String code;
    private final String message;
    private final String detail;

    CatalogueErrors(String code, String message, String detail) {
        this.code = code;
        this.message = message;
        this.detail = detail;
    }
}
