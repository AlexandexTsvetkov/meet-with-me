package ru.aston.meet.service.auth;

import ru.aston.meet.dto.auth.AuthenticationRequest;
import ru.aston.meet.dto.auth.AuthenticationResponse;
import ru.aston.meet.dto.auth.RegisterRequest;

public interface AutheticationService {

    AuthenticationResponse register(RegisterRequest registerRequest);
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
