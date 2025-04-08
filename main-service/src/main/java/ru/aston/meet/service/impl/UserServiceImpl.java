package ru.aston.meet.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.aston.meet.dto.UserDto;
import ru.aston.meet.exception.NotFoundException;
import ru.aston.meet.mapper.UserMapper;
import ru.aston.meet.model.User;
import ru.aston.meet.repository.UserRepository;
import ru.aston.meet.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl  implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto getUserInfo(String email) {

        Optional<User> user = userRepository.findByEmail(email);

        if(user.isPresent()) {
            return userMapper.toUserDto(user.get());
        } else {
            throw new NotFoundException("User not found, email: " + email);
        }
    }
}
