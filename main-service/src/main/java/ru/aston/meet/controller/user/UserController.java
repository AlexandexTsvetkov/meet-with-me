package ru.aston.meet.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aston.meet.dto.user.UserDto;
import ru.aston.meet.service.user.UserService;

import java.util.List;

/**
 * Контроллер для управления пользователями.
 * Предоставляет эндпоинты для получения списка пользователей и информации о конкретном пользователе.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "User Controller", description = "Managing user")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    private final UserService userService;

    /**
     * Получить список всех пользователей.
     *
     * @return список {@link UserDto} всех пользователей
     */
    @GetMapping
    @Operation(summary = "Get all users ", description = "Gets the list of users")
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    /**
     * Получить информацию о пользователе по email.
     *
     * @param email email пользователя
     * @return {@link ResponseEntity} с {@link UserDto} найденного пользователя
     */
    @GetMapping("/{email}")
    @Operation(summary = "Get user details", description = "Gets the details of the specified user")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserInfo(email));
    }
}