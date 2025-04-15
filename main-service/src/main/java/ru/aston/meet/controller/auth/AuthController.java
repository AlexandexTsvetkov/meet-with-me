package ru.aston.meet.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aston.meet.dto.auth.AuthenticationRequest;
import ru.aston.meet.dto.auth.AuthenticationResponse;
import ru.aston.meet.dto.auth.RegisterRequest;
import ru.aston.meet.service.auth.AutheticationService;

/**
 * Контроллер для обработки запросов, связанных с аутентификацией и регистрацией пользователей.
 * <p>
 * Предоставляет эндпоинты для регистрации нового пользователя и аутентификации (входа) пользователя.
 * </p>
 *
 * @author ВашеИмя
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    /**
     * Сервис для обработки логики аутентификации и регистрации.
     */
    private final AutheticationService autheticationService;

    /**
     * Регистрирует нового пользователя в системе.
     *
     * @param request объект с данными пользователя для регистрации
     * @return ответ с результатом регистрации, в том числе с JWT-токеном или иной необходимой информацией
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(autheticationService.register(request));
    }

    /**
     * Аутентифицирует пользователя (вход по логину и паролю).
     *
     * @param request объект с учётными данными пользователя
     * @return ответ с результатом аутентификации, в том числе с JWT-токеном или иной необходимой информацией
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(autheticationService.authenticate(request));
    }
}
