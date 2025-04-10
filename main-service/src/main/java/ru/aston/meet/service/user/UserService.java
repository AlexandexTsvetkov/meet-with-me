package ru.aston.meet.service.user;

import ru.aston.meet.dto.user.UserDto;

import java.util.List;

public interface UserService {

    /**
     * Получение пользователя по email
     */
    UserDto getUserInfo(String email);

    /**
     * Получение списка всех пользователей
     */
    List<UserDto> getAll();
}