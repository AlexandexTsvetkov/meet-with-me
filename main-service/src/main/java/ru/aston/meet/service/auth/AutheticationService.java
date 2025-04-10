package ru.aston.meet.service.auth;

import ru.aston.meet.dto.auth.AuthenticationRequest;
import ru.aston.meet.dto.auth.AuthenticationResponse;
import ru.aston.meet.dto.auth.RegisterRequest;

public interface AutheticationService {

    /**
     * Авторизует пользователя по логину и паролю
     */
    AuthenticationResponse register(RegisterRequest registerRequest);

    /**
     * Аутентифицирует пользователя по логину и паролю
     */
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
