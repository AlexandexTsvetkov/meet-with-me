package ru.aston.meet.service;

import ru.aston.meet.dto.UserDto;

public interface UserService {

    UserDto getUserInfo(String email);
}
