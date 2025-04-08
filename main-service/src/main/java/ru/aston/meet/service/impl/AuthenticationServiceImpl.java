package ru.aston.meet.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.aston.meet.config.JwtService;
import ru.aston.meet.dto.AuthenticationRequest;
import ru.aston.meet.dto.AuthenticationResponse;
import ru.aston.meet.dto.RegisterRequest;
import ru.aston.meet.exception.AlreadyExistsException;
import ru.aston.meet.mapper.UserMapper;
import ru.aston.meet.model.User;
import ru.aston.meet.repository.UserRepository;
import ru.aston.meet.service.AutheticationService;

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
