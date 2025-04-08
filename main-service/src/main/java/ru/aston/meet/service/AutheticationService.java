package ru.aston.meet.service;

import ru.aston.meet.dto.AuthenticationRequest;
import ru.aston.meet.dto.AuthenticationResponse;
import ru.aston.meet.dto.RegisterRequest;

public interface AutheticationService {

    AuthenticationResponse register(RegisterRequest registerRequest);
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
