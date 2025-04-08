package ru.aston.meet.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Класс для представления запроса на аутентификацию пользователя.
 */
@Getter
@Setter
@NoArgsConstructor
public class AuthenticationRequest {

    /*
     * Электронная почта пользователя.
     * Должна быть действительной (соответствовать формату электронной почты).
     */
    @NotBlank(message = "Email cannot be null or empty")
    @Email(message = "Email should be valid")
    private String email;

    /*
     * Пароль пользователя.
     * Не должен быть пустым и должен соответствовать следующим критериям:
     * - Минимум 6 и максимум 20 символов
     * - Должен содержать хотя бы одну букву верхнего регистра
     * - Должен содержать хотя бы одну букву нижнего регистра
     * - Должен содержать хотя бы одну цифру
     * - Должен содержать хотя бы один специальный символ (!@#$%)
     */
    @NotBlank(message = "Parameter 'password' cannot be null or empty")
    @Pattern(
            regexp = "^(?=.*[a-zа-я])(?=.*[A-ZА-Я])(?=.*\\d)(?=.*[!@#$%])[A-ZА-Яa-zа-я\\d!@#$%]{6,20}$",
            message = "Incorrect password."
    )
    private String password;
}
