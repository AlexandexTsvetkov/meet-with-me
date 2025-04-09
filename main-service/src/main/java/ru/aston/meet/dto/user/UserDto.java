package ru.aston.meet.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.aston.meet.model.user.Role;

/*
 * Выходной DTO (Data Transfer Object) для представления информации о пользователе.
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    /*
     * Идентификатор пользователя.
     */
    private Long id;

    /*
     * Электронная почта пользователя.
     * Должна соответствовать формату электронной почты.
     */
    @Email(message = "Email должен быть корректным")
    @NotBlank(message = "Email не должен быть пустым")
    private String email;

    /*
     * Имя пользователя.
     * Не должно быть пустым.
     */
    @NotBlank(message = "Имя не должно быть пустым")
    private String name;

    /*
     * Статус пользователя.
     */
    private String status;

    /*
     * Данные о блокировке пользователя.
     * Может быть true, если пользователь заблокирован, иначе false.
     */
    private Boolean blocked;

    /*
     * Роль пользователя (например, USER, ADMIN).
     */
    private Role role;
}
