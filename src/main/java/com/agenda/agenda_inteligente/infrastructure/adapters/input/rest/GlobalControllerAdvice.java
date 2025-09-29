package com.agenda.agenda_inteligente.infrastructure.adapters.input.rest;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.agenda.agenda_inteligente.domain.exceptions.UserDuplicatedException;
import com.agenda.agenda_inteligente.domain.exceptions.UserNotFoundException;
import com.agenda.agenda_inteligente.domain.model.ErrorResponse;
import com.agenda.agenda_inteligente.infrastructure.adapters.input.rest.model.ApiResponse;
import com.agenda.agenda_inteligente.utils.CatalogueErrors;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponse handleUserNotFoundException() {
        return ErrorResponse.builder()
                .code(CatalogueErrors.USER_NOT_FOUND.getCode())
                .message(CatalogueErrors.USER_NOT_FOUND.getMessage())
                .detail(CatalogueErrors.USER_NOT_FOUND.getDetail())
                .timestamp(java.time.LocalDateTime.now())
                .build();
    }

    // ACCESO DENEGADO
    @ResponseStatus(HttpStatus.FORBIDDEN) // 403
    @ExceptionHandler(AccessDeniedException.class)
    public ApiResponse<Object> handleAccessDeniedException(AccessDeniedException ex) {
        return new ApiResponse<>(
                HttpStatus.FORBIDDEN.value(),
                CatalogueErrors.ACCESS_DENIED.getMessage(),
                Collections.singletonList(ex.getMessage()));
    }

    // USER DUPLICATED
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserDuplicatedException.class)
    public ApiResponse<Object> handleUserDuplicatedException(UserDuplicatedException ex) {
        return new ApiResponse<>(
                HttpStatus.CONFLICT.value(),
                CatalogueErrors.USER_EMAIL_ALREADY_EXISTS.getMessage(),
                Collections.singletonList(ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();

        return ErrorResponse.builder()
                .code(CatalogueErrors.REQUEST_INVALID.getCode())
                .message(CatalogueErrors.REQUEST_INVALID.getMessage())
                .details(result.getFieldErrors().stream()
                        .map(error -> error.getField() + ": " + error.getDefaultMessage())
                        .toList())
                .timestamp(java.time.LocalDateTime.now())
                .build();
    }

    // ERRORES NO CONTROLADOS
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiResponse<Object> handleAllUncaughtException(Exception exception) {
        return new ApiResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                CatalogueErrors.ERROR_GENERICO.getMessage(),
                Collections.singletonList(exception.getMessage()));
    }

}
