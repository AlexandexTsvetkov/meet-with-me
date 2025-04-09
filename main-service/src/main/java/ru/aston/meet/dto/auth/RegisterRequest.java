package ru.aston.meet.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Класс, представляющий запрос на регистрацию пользователя.
 */
@Getter
@Setter
@NoArgsConstructor
public class RegisterRequest {

    /*
     * Электронная почта пользователя.
     * Должна быть действительной и не может быть пустой.
     */
    @NotBlank(message = "Email cannot be null or empty")
    @Email(message = "Email should be valid")
    private String email;

    /*
     * Имя пользователя.
     * Не должно быть пустым.
     */
    @NotBlank(message = "Name cannot be null or empty")
    private String name;

    /*
     * Пароль пользователя.
     * Не должен быть пустым и должен соответствовать следующим критериям:
     * - Минимум 6 и максимум 20 символов.
     * - Должен содержать хотя бы одну букву верхнего регистра.
     * - Должен содержать хотя бы одну букву нижнего регистра.
     * - Должен содержать хотя бы одну цифру.
     * - Должен содержать хотя бы один специальный символ (!@#$%).
     */
    @NotBlank(message = "Parameter 'password' cannot be null or empty")
    @Pattern(
            regexp = "^(?=.*[a-zа-я])(?=.*[A-ZА-Я])(?=.*\\d)(?=.*[!@#$%])[A-ZА-Яa-zа-я\\d!@#$%]{6,20}$",
            message = "Incorrect password."
    )
    private String password;
}
