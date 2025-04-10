package ru.aston.meet.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.aston.meet.model.user.Role;

/**
 * Выходной DTO (Data Transfer Object) для представления информации о пользователе.
 *
 * <p>{@link #id} - Идентификатор пользователя.</p>
 * <p>{@link #email} - Электронная почта пользователя. Должна соответствовать формату электронной почты.</p>
 * <p>{@link #name} - Имя пользователя. Не должно быть пустым.</p>
 * <p>{@link #role} - Роль пользователя (например, USER, ADMIN)..</p>
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private Long id;

    @Email(message = "Email должен быть корректным")
    @NotBlank(message = "Email не должен быть пустым")
    private String email;

    @NotBlank(message = "Имя не должно быть пустым")
    private String name;

    private Role role;
}