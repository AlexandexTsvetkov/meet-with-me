package ru.aston.meet.service.impl.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.aston.meet.config.security.JwtService;
import ru.aston.meet.dto.auth.AuthenticationRequest;
import ru.aston.meet.dto.auth.AuthenticationResponse;
import ru.aston.meet.dto.auth.RegisterRequest;
import ru.aston.meet.exception.AlreadyExistsException;
import ru.aston.meet.mapper.user.UserMapper;
import ru.aston.meet.model.user.User;
import ru.aston.meet.repository.user.UserRepository;
import ru.aston.meet.service.auth.AutheticationService;

/**
 * Сервисный слой для авторизации пользователей.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AutheticationService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest registerRequest) {

        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new AlreadyExistsException("User with email = " + registerRequest.getEmail() + " already exists");
        }

        User user = userRepository.save(userMapper.toUser(registerRequest));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        var user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
