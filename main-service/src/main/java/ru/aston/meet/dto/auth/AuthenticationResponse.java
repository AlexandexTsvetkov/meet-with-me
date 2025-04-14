package ru.aston.meet.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/*
 * Класс, представляющий ответ аутентификации пользователя.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
public class AuthenticationResponse {

    /*
     * Токен доступа, полученный после успешной аутентификации.
     */
    private String token;
}
