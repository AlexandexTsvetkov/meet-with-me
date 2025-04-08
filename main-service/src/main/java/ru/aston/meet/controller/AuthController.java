package ru.aston.meet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aston.meet.dto.AuthenticationRequest;
import ru.aston.meet.dto.AuthenticationResponse;
import ru.aston.meet.dto.RegisterRequest;
import ru.aston.meet.service.AutheticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AutheticationService autheticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register (@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(autheticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register (@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(autheticationService.authenticate(request));
    }
}
