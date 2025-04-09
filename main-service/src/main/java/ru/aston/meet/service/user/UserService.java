package ru.aston.meet.service.user;

import ru.aston.meet.dto.user.UserDto;

public interface UserService {

    UserDto getUserInfo(String email);
}
